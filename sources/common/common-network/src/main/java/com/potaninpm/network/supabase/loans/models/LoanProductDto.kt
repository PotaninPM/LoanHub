package com.potaninpm.network.supabase.loans.models

import com.google.gson.annotations.SerializedName

data class LoanProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon_name") val iconName: String,
    @SerializedName("gradient_start") val gradientStart: String,
    @SerializedName("gradient_end") val gradientEnd: String,
    @SerializedName("is_popular") val isPopular: Boolean = false,
    @SerializedName("sort_order") val sortOrder: Int = 0
)
