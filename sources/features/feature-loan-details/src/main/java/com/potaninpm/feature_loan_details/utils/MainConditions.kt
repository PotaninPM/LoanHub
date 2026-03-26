package com.potaninpm.feature_loan_details.utils

data class MainConditions(
    val minAmount: Long,
    val maxAmount: Long,
    val minTermInMonths: Int?,
    val maxTermInMonths: Int?,
    val interestRateFrom: Double,
    val currency: Currency,
    val initialPaymentPercentFrom: Int? = null,
    val gracePeriodDays: Int? = null,
    val minMonthlyPaymentPercent: Int? = null
)
