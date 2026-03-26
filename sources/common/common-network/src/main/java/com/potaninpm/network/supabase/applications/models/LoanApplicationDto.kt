package com.potaninpm.network.supabase.applications.models

import com.google.gson.annotations.SerializedName

data class LoanApplicationDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("user_id") val userId: String? = null,
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: String,
    @SerializedName("status") val status: String = "DRAFT",
    @SerializedName("income_currency") val incomeCurrency: String = "RUB",
    @SerializedName("amount_currency") val amountCurrency: String = "RUB",
    @SerializedName("fields") val fields: String,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null
)