package com.potaninpm.feature_application_preview.presentation.state

import com.potaninpm.database.models.FormField
import com.potaninpm.uikit.presentation.details.DetailItem

data class PreviewState(
    val fields: List<FormField> = emptyList(),
    val convertedIncome: Int? = null,
    val convertedAmount: Int? = null,
    val incomeCurrency: String = "RUB",
    val amountCurrency: String = "RUB",
    val detailItems: List<DetailItem> = emptyList(),
    val isLoading: Boolean = false
)