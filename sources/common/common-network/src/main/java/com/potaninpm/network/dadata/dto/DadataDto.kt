package com.potaninpm.network.dadata.dto

import com.google.gson.annotations.SerializedName

data class DadataRequest(
    val query: String,
    val count: Int = 10
)

data class DadataResponse(
    val suggestions: List<DadataSuggestion>
)

data class DadataSuggestion(
    val value: String,
    @SerializedName("unconstrained_value")
    val unconstrainedValue: String?
)
