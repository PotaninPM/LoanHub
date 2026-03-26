package com.potaninpm.feature_loan_application.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.feature_loan_application.R
import com.potaninpm.feature_loan_application.di.LoanApplicationComponentHolder
import com.potaninpm.feature_loan_application.presentation.helpers.groupFieldsIntoSections
import com.potaninpm.feature_loan_application.presentation.ui.components.FormFieldItem
import com.potaninpm.feature_loan_application.presentation.ui.components.FormWizardIndicator
import com.potaninpm.feature_loan_application.presentation.viewmodel.LoanApplicationViewModel
import com.potaninpm.uikit.presentation.buttons.LoanHubUiButton
import com.potaninpm.uikit.presentation.modals.LoanHubUiConfirmationModal
import com.potaninpm.uikit.presentation.text.LoanHubUiText
import com.potaninpm.uikit.utils.loanTypeTitle
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanApplicationScreen(
    loanType: String,
    draftId: String? = null,
    importedFieldsJson: String? = null,
    onNavigateBack: () -> Unit,
    onNavigateToPreview: (String, String, String, String) -> Unit
) {
    val component = LoanApplicationComponentHolder.get()

    val viewModel: LoanApplicationViewModel = viewModel(
        factory = component.provideViewModelFactory()
    )

    val fields by viewModel.fields.collectAsStateWithLifecycle()
    val incomeCurrency by viewModel.selectedIncomeCurrency.collectAsStateWithLifecycle()
    val amountCurrency by viewModel.selectedAmountCurrency.collectAsStateWithLifecycle()
    val currencies by viewModel.currencies.collectAsStateWithLifecycle()
    val submissionSuccess by viewModel.submissionSuccess.collectAsStateWithLifecycle()

    val sections by remember(fields) {
        derivedStateOf { groupFieldsIntoSections(fields) }
    }

    val pageCount = sections.size.coerceAtLeast(1)
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )
    val scope = rememberCoroutineScope()

    var saveToDraft by remember {
        mutableStateOf(false)
    }

    val showPrefillDialog by viewModel.showPrefillDialog.collectAsStateWithLifecycle()
    val prefillLoading by viewModel.prefillLoading.collectAsStateWithLifecycle()

    LaunchedEffect(loanType) {
        if (draftId != null) {
            viewModel.loadDraft(draftId)
        } else if (importedFieldsJson != null) {
            viewModel.loadFromImport(loanType, importedFieldsJson)
        } else {
            viewModel.initForm(loanType)
        }
    }

    LaunchedEffect(submissionSuccess) {
        if (submissionSuccess) {
            val json = Json.encodeToString(fields)
            onNavigateToPreview(json, incomeCurrency, amountCurrency, loanType)
            viewModel.resetSubmission()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.scrollToField.collect { fieldId ->
            val pageIndex = sections.indexOfFirst { section ->
                section.fields.any { it.id == fieldId }
            }
            if (pageIndex != -1) {
                pagerState.animateScrollToPage(pageIndex)
            }
        }
    }

    if (saveToDraft) {
        LoanHubUiConfirmationModal(
            title = stringResource(R.string.save_draft_title),
            text = stringResource(R.string.save_draft_message),
            confirmButtonText = stringResource(R.string.action_save),
            denyButtonText = stringResource(R.string.action_no),
            onConfirm = {
                saveToDraft = false
                viewModel.saveDraft()
                onNavigateBack()
            },
            onDismiss = {
                saveToDraft = false
            },
            onDeny = {
                saveToDraft = false
                onNavigateBack()
            }
        )
    }

    if (showPrefillDialog) {
        LoanHubUiConfirmationModal(
            title = stringResource(R.string.prefill_title),
            text = stringResource(R.string.prefill_message),
            confirmButtonText = stringResource(R.string.action_yes_fill),
            denyButtonText = stringResource(R.string.action_no_thanks),
            onConfirm = { viewModel.onPrefillDialogResult(true) },
            onDeny = { viewModel.onPrefillDialogResult(false) },
            onDismiss = {}
        )
    }
    
    if (prefillLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { LoanHubUiText(style = MaterialTheme.typography.titleMedium, text = stringResource(loanTypeTitle(loanType))) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (fields.any { it.value != "" }) {
                                saveToDraft = true
                            } else {
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_desc_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            FormWizardIndicator(
                currentPage = pagerState.currentPage,
                pageCount = sections.size,
                sectionTitleRes = sections.getOrNull(pagerState.currentPage)?.titleRes ?: 0
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                userScrollEnabled = true,
                beyondViewportPageCount = 1
            ) { page ->
                if (page < sections.size) {
                    val section = sections[page]
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(4.dp))
                        }

                        items(section.fields, key = { it.id }) { field ->
                            FormFieldItem(
                                field = field,
                                viewModel = viewModel,
                                incomeCurrency = incomeCurrency,
                                amountCurrency = amountCurrency,
                                currencies = currencies,
                                validation = viewModel.getValidation(field)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (pagerState.currentPage > 0) {
                    LoanHubUiButton(
                        text = stringResource(R.string.action_back),
                        modifier = Modifier.weight(1f),
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        }
                    )
                }

                if (pagerState.currentPage < pageCount - 1) {
                    LoanHubUiButton(
                        text = stringResource(R.string.action_next),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        }
                    )
                } else {
                    LoanHubUiButton(
                        text = stringResource(R.string.action_submit_application),
                        modifier = Modifier.weight(1f),
                        onClick = viewModel::submit,
                        isLoading = false
                    )
                }
            }
        }
    }
}
