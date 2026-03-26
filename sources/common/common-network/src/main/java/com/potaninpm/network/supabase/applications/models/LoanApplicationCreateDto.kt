package com.potaninpm.network.supabase.applications.models

import com.google.gson.annotations.SerializedName

data class LoanApplicationCreateDto(
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String,
    @SerializedName("status") val status: String = "SUBMITTED",
    @SerializedName("income_currency") val incomeCurrency: String = "RUB",
    @SerializedName("amount_currency") val amountCurrency: String = "RUB",
    @SerializedName("fields") val fields: String
)
