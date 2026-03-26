package com.potaninpm.feature_loan_details.utils

data class BorrowerRequirements(
    val minAge: Int,
    val maxAge: Int,
    val citizenshipRequired: Boolean,
    val minWorkExperienceMonths: Int,
    val incomeConfirmationRequired: Boolean
)
