package com.potaninpm.feature_edit_profile.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.feature_edit_profile.R
import com.potaninpm.feature_edit_profile.di.EditProfileComponentHolder
import com.potaninpm.feature_edit_profile.presentation.components.SectionCard
import com.potaninpm.feature_edit_profile.presentation.state.EditProfileState
import com.potaninpm.feature_edit_profile.presentation.viewmodel.EditProfileViewModel
import com.potaninpm.uikit.presentation.DefaultErrorView
import com.potaninpm.uikit.presentation.buttons.LoanHubUiButton
import com.potaninpm.uikit.presentation.inputs.LoanHubUiDatePicker
import com.potaninpm.uikit.presentation.inputs.LoanHubUiTextField
import com.potaninpm.uikit.presentation.inputs.LoanHubUiTextFieldValidation
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val component = EditProfileComponentHolder.get()
    val viewModel: EditProfileViewModel = viewModel(
        factory = component.provideViewModelFactory()
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saveSuccess) {
        if (state.saveSuccess) {
            onSaveSuccess()
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { LoanHubUiText(style = MaterialTheme.typography.titleMedium, text = stringResource(R.string.title_edit_profile)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_desc_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                }
            }
            state.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DefaultErrorView(
                        onRetry = {
                            viewModel.loadProfile()
                        }
                    )
                }
            }
            else -> {
                EditProfileScreenContent(state, padding, viewModel)
            }
        }
    }
}

@Composable
private fun EditProfileScreenContent(
    state: EditProfileState,
    padding: PaddingValues,
    viewModel: EditProfileViewModel
) {
    val profile = state.profile
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionCard(title = stringResource(R.string.section_personal_info)) {
                LoanHubUiTextField(
                    value = state.fioInput,
                    onValueChange = { viewModel.updateFio(it) },
                    label = stringResource(R.string.label_fio),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Required()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiDatePicker(
                    value = profile.birthDate,
                    onValueChange = { v -> viewModel.updateField { copy(birthDate = v) } },
                    label = stringResource(R.string.label_birth_date),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            SectionCard(title = stringResource(R.string.section_passport_data)) {
                LoanHubUiTextField(
                    value = profile.passportSeriesNumber,
                    onValueChange = { v -> viewModel.updateField { copy(passportSeriesNumber = v) } },
                    label = stringResource(R.string.label_passport_series_number),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Regex(
                        pattern = "^\\d{4} \\d{6}$",
                        errorMessage = stringResource(R.string.error_format_passport)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.passportIssuedBy,
                    onValueChange = { v -> viewModel.updateField { copy(passportIssuedBy = v) } },
                    label = stringResource(R.string.label_passport_issued_by),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Required()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiDatePicker(
                    value = profile.passportIssueDate,
                    onValueChange = { v -> viewModel.updateField { copy(passportIssueDate = v) } },
                    label = stringResource(R.string.label_passport_date),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.passportDivisionCode,
                    onValueChange = { v -> viewModel.updateField { copy(passportDivisionCode = v) } },
                    label = stringResource(R.string.label_passport_division_code),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Regex(
                        pattern = "^\\d{3}-\\d{3}$",
                        errorMessage = stringResource(R.string.error_format_division_code)
                    )
                )
            }
        }

        item {
            SectionCard(title = stringResource(R.string.section_documents)) {
                LoanHubUiTextField(
                    value = profile.inn,
                    onValueChange = { v -> viewModel.updateField { copy(inn = v) } },
                    label = stringResource(R.string.label_inn),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Regex(
                        pattern = "^\\d{10,12}$",
                        errorMessage = stringResource(R.string.error_format_inn)
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.snils,
                    onValueChange = { v -> viewModel.updateField { copy(snils = v) } },
                    label = stringResource(R.string.label_snils),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Regex(
                        pattern = "^\\d{3}-\\d{3}-\\d{3} \\d{2}$",
                        errorMessage = stringResource(R.string.error_format_snils)
                    )
                )
            }
        }

        item {
            SectionCard(title = stringResource(R.string.section_contact_info)) {
                LoanHubUiTextField(
                    value = profile.phone,
                    onValueChange = { v -> viewModel.updateField { copy(phone = v) } },
                    label = stringResource(R.string.label_phone),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Phone()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.email,
                    onValueChange = { v -> viewModel.updateField { copy(email = v) } },
                    label = stringResource(R.string.label_email),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Email()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.addressRegistration,
                    onValueChange = { v -> viewModel.updateField { copy(addressRegistration = v) } },
                    label = stringResource(R.string.label_address_registration),
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Required()
                )
                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.postalCode,
                    onValueChange = { v -> viewModel.updateField { copy(postalCode = v) } },
                    label = stringResource(R.string.label_postal_code),
                    modifier = Modifier.fillMaxWidth(),
                    validationType = LoanHubUiTextFieldValidation.Regex(
                        pattern = "^\\d{6}$",
                        errorMessage = stringResource(R.string.error_format_postal_code)
                    )
                )
            }
        }

        item {
            SectionCard(title = stringResource(R.string.section_work_info)) {
                LoanHubUiTextField(
                    value = profile.employerName,
                    onValueChange = { v -> viewModel.updateField { copy(employerName = v) } },
                    label = stringResource(R.string.label_employer_name),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.employerInn,
                    onValueChange = { v -> viewModel.updateField { copy(employerInn = v) } },
                    label = stringResource(R.string.label_employer_inn),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.jobTitle,
                    onValueChange = { v -> viewModel.updateField { copy(jobTitle = v) } },
                    label = stringResource(R.string.label_job_title),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.workExperience,
                    onValueChange = { v -> viewModel.updateField { copy(workExperience = v) } },
                    label = stringResource(R.string.label_work_experience),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LoanHubUiTextField(
                    value = profile.monthlyIncome?.toString() ?: "",
                    onValueChange = { v -> viewModel.updateField { copy(monthlyIncome = v.toIntOrNull()) } },
                    label = stringResource(R.string.label_monthly_income),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        if (state.error != null) {
            item {
                LoanHubUiText(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        item {
            LoanHubUiButton(
                text = if (state.isSaving) stringResource(R.string.btn_saving) else stringResource(R.string.btn_save),
                modifier = Modifier.fillMaxWidth(),
                isEnabled = !state.isSaving,
                onClick = { viewModel.saveProfile() }
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
