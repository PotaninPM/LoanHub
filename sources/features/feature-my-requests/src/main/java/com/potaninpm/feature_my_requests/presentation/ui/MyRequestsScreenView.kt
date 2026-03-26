package com.potaninpm.feature_my_requests.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.feature_my_requests.di.MyRequestsComponentHolder
import com.potaninpm.feature_my_requests.presentation.models.LoanCategory
import com.potaninpm.feature_my_requests.presentation.state.ExportFormat
import com.potaninpm.feature_my_requests.presentation.ui.components.DraftsSection
import com.potaninpm.feature_my_requests.presentation.ui.components.ExportSelectionDialog
import com.potaninpm.feature_my_requests.presentation.ui.components.MyRequestsSkeleton
import com.potaninpm.feature_my_requests.presentation.ui.components.MyRequestsTopBar
import com.potaninpm.feature_my_requests.presentation.ui.components.SubmittedApplicationsList
import com.potaninpm.feature_my_requests.presentation.viewmodel.MyRequestsViewModel
import com.potaninpm.uikit.presentation.DefaultErrorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRequestsScreenView(
    refreshTrigger: Long = 0,
    onOfferClick: (String, String) -> Unit = { _, _ -> },
    onDraftClick: (String, String) -> Unit = { _, _ -> },
    onNavigateToApplication: (loanType: String, draftId: String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val viewModel: MyRequestsViewModel = viewModel(
        factory = MyRequestsComponentHolder.get().provideViewModelFactory()
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(refreshTrigger) {
        if (refreshTrigger > 0) {
            viewModel.refresh()
        }
    }

    val jsonImportLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            val content = context.contentResolver.openInputStream(it)?.bufferedReader().use { r -> r?.readText() }
            content?.let { json ->
                viewModel.importApplicationAsDraft(json) { loanType, draftId ->
                    onNavigateToApplication(loanType, draftId)
                }
            }
        }
    }

    val csvImportLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            val content = context.contentResolver.openInputStream(it)?.bufferedReader().use { r -> r?.readText() }
            content?.let { csv ->
                viewModel.importApplicationCsvAsDraft(csv) { loanType, draftId ->
                    onNavigateToApplication(loanType, draftId)
                }
            }
        }
    }

    val jsonExportLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri: Uri? ->
        uri?.let {
            val content = viewModel.exportSelectedJson()
            context.contentResolver.openOutputStream(it)?.bufferedWriter().use { w -> w?.write(content) }
            viewModel.dismissExportDialog()
        }
    }

    val csvExportLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/csv")) { uri: Uri? ->
        uri?.let {
            val content = viewModel.exportSelectedCsv()
            context.contentResolver.openOutputStream(it)?.bufferedWriter().use { w -> w?.write(content) }
            viewModel.dismissExportDialog()
        }
    }

    val topAppBarColor by animateColorAsState(
        targetValue = if (state.drafts.isNotEmpty()) {
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        } else {
            MaterialTheme.colorScheme.background
        },
        label = "TopAppBarColor"
    )

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.importError) {
        state.importError?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearImportError()
        }
    }

    if (state.showExportDialog) {
        ExportSelectionDialog(
            applications = state.submittedApplications,
            selectedId = state.selectedExportId,
            onSelect = { viewModel.toggleExportSelection(it) },
            onConfirm = {
                when (state.exportFormat) {
                    ExportFormat.JSON -> jsonExportLauncher.launch("applications.json")
                    ExportFormat.CSV -> csvExportLauncher.launch("applications.csv")
                    null -> {}
                }
            },
            onDismiss = { viewModel.dismissExportDialog() }
        )
    }

    Scaffold(
        topBar = {
            MyRequestsTopBar(
                containerColor = topAppBarColor,
                onImportJson = { jsonImportLauncher.launch(arrayOf("application/json")) },
                onImportCsv = { csvImportLauncher.launch(arrayOf("text/comma-separated-values", "text/csv")) },
                onShowExportDialog = { format -> viewModel.showExportDialog(format) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    AnimatedVisibility(visible = state.isImporting || state.isExporting) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }

                    DraftsSection(
                        drafts = state.drafts,
                        isExpanded = state.isDraftsExpanded,
                        onToggleExpanded = { viewModel.toggleDraftsExpanded() },
                        onDraftClick = onDraftClick,
                        onDeleteDraft = { viewModel.deleteApplication(it) }
                    )

                    SubmittedApplicationsList(
                        submittedApplications = state.submittedApplications,
                        filters = state.filters,
                        selectedFilterType = state.filterType?.id,
                        totalSubmittedCount = state.totalSubmittedCount,
                        isLoading = state.isLoading,
                        isAdmin = state.isAdmin,
                        updatingStatusIds = state.updatingStatusIds,
                        onFilterClick = { type ->
                            if (type == "all") viewModel.setFilter(null)
                            else viewModel.setFilter(LoanCategory.fromId(type))
                        },
                        onApplicationClick = { id -> onOfferClick(id, "") },
                        onApprove = { id -> viewModel.approveApplication(id) },
                        onReject = { id -> viewModel.rejectApplication(id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                if (state.isLoading) {
                    MyRequestsSkeleton(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    )
                } else if (state.error != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {
                        DefaultErrorView(onRetry = { viewModel.loadData() })
                    }
                }
            }
        }
    }
}
