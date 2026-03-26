package com.potaninpm.feature_loan_details.utils

data class LoanDetails(
    val type: CreditType,
    val title: String,
    val description: String,
    val mainConditions: MainConditions,
    val borrowerRequirements: BorrowerRequirements
)
