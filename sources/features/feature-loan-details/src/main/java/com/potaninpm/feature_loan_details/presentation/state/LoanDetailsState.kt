package com.potaninpm.feature_loan_details.presentation.state

import com.potaninpm.feature_loan_details.utils.LoanDetails

data class DetailsState(
    val loanDetails: LoanDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)