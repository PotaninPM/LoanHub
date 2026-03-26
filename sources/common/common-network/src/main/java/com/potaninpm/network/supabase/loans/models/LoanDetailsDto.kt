package com.potaninpm.network.supabase.loans.models

import com.google.gson.annotations.SerializedName

data class LoanDetailsDto(
    @SerializedName("product_id") val productId: String,
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("main_conditions") val mainConditions: MainConditionsDto,
    @SerializedName("borrower_requirements") val borrowerRequirements: BorrowerRequirementsDto
)

data class MainConditionsDto(
    @SerializedName("minAmount") val minAmount: Long,
    @SerializedName("maxAmount") val maxAmount: Long,
    @SerializedName("minTermInMonths") val minTermInMonths: Int?,
    @SerializedName("maxTermInMonths") val maxTermInMonths: Int?,
    @SerializedName("interestRateFrom") val interestRateFrom: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("initialPaymentPercentFrom") val initialPaymentPercentFrom: Int?,
    @SerializedName("gracePeriodDays") val gracePeriodDays: Int?,
    @SerializedName("minMonthlyPaymentPercent") val minMonthlyPaymentPercent: Int?
)

data class BorrowerRequirementsDto(
    @SerializedName("minAge") val minAge: Int,
    @SerializedName("maxAge") val maxAge: Int,
    @SerializedName("citizenshipRequired") val citizenshipRequired: Boolean,
    @SerializedName("minWorkExperienceMonths") val minWorkExperienceMonths: Int,
    @SerializedName("incomeConfirmationRequired") val incomeConfirmationRequired: Boolean
)
