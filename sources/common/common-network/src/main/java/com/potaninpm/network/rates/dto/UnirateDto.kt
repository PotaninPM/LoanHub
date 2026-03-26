package com.potaninpm.network.rates.dto

import com.google.gson.annotations.SerializedName

data class UnirateResponse(
    @SerializedName("amount") val amount: Double? = null,
    @SerializedName("base") val base: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("rates") val rates: Map<String, Double>? = null,
    @SerializedName("to") val to: String? = null,
    @SerializedName("result") val result: Double? = null,
    @SerializedName("rate") val rate: Double? = null
)
