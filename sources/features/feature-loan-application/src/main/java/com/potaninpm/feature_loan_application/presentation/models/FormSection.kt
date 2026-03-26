package com.potaninpm.feature_loan_application.presentation.models

import com.potaninpm.database.models.FormField

data class FormSection(
    val titleRes: Int,
    val fields: List<FormField>
)