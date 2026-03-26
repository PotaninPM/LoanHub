package com.potaninpm.feature_application_details.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.feature_application_details.di.ApplicationDetailsComponentHolder
import com.potaninpm.feature_application_details.presentation.ui.components.ApplicationDetailsSkeleton
import com.potaninpm.feature_application_details.presentation.ui.components.ApplicationHeaderCard
import com.potaninpm.feature_application_details.presentation.ui.components.ProbabilityCard
import com.potaninpm.feature_application_details.presentation.ui.components.SimilarApplicationsTable
import com.potaninpm.feature_application_details.presentation.utils.ProbabilityLevel
import com.potaninpm.feature_application_details.presentation.viewmodel.ApplicationDetailsViewModel
import com.potaninpm.feature_loan_details.R
import com.potaninpm.uikit.presentation.DefaultErrorView
import com.potaninpm.uikit.presentation.cards.DetailInfoCard
import com.potaninpm.uikit.presentation.details.LoanHubUiDetailsTable
import com.potaninpm.uikit.presentation.modals.LoanHubUiConfirmationModal
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationDetailsScreen(
    applicationId: String,
    onBack: () -> Unit,
    onDeleted: () -> Unit = {}
) {
    val component = ApplicationDetailsComponentHolder.get()

    val viewModel: ApplicationDetailsViewModel = viewModel(
        factory = component.provideViewModelFactory(applicationId)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        LoanHubUiConfirmationModal(
            title = stringResource(R.string.delete_dialog_title),
            text = stringResource(R.string.delete_dialog_message),
            confirmButtonText = stringResource(R.string.delete_dialog_confirm),
            denyButtonText = stringResource(R.string.delete_dialog_cancel),
            onConfirm = {
                showDeleteDialog = false
                viewModel.deleteApplication(onDeleted)
            },
            onDeny = { showDeleteDialog = false },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { LoanHubUiText(style = MaterialTheme.typography.titleMedium, text = stringResource(R.string.title_application_details)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_desc_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.content_desc_delete),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        val app = state.application

        when {
            state.isLoading -> {
                ApplicationDetailsSkeleton()
            }
            state.error != null -> {
                DefaultErrorView(onRetry = { viewModel.loadData() })
            }
            app == null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoanHubUiText(stringResource(R.string.error_application_not_found))
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ApplicationHeaderCard(type = app.type, date = app.date)
                    }

                    if (state.probability != ProbabilityLevel.UNKNOWN) {
                        item {
                            ProbabilityCard(
                                probability = state.probability,
                                payment = state.payment
                            )
                        }
                    }

                    item {
                        LoanHubUiText(
                            text = stringResource(R.string.title_application_data),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )

                        LoanHubUiDetailsTable(items = state.detailItems)
                    }

                    item {
                        DetailInfoCard(
                            label = stringResource(R.string.label_income_currency),
                            value = app.incomeCurrency
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        DetailInfoCard(
                            label = stringResource(R.string.label_loan_currency),
                            value = app.amountCurrency
                        )
                    }

                    if (state.similarApplications.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            LoanHubUiText(
                                text = stringResource(R.string.title_similar_applications),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        item {
                            SimilarApplicationsTable(
                                similarApplications = state.similarApplications
                            )
                        }
                    }

                    item { Spacer(modifier = Modifier.height(32.dp)) }
                }
            }
        }
    }
}
