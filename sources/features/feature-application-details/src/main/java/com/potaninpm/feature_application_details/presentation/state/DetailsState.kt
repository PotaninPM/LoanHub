package com.potaninpm.feature_application_details.presentation.state

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_application_details.presentation.utils.ProbabilityLevel
import com.potaninpm.uikit.presentation.details.DetailItem

data class DetailsState(
    val application: LoanApplication? = null,
    val probability: ProbabilityLevel = ProbabilityLevel.UNKNOWN,
    val payment: Int = 0,
    val similarApplications: List<LoanApplication> = emptyList(),
    val convertedIncome: Int? = null,
    val convertedAmount: Int? = null,
    val detailItems: List<DetailItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAdmin: Boolean = false,
    val isUpdatingStatus: Boolean = false
)