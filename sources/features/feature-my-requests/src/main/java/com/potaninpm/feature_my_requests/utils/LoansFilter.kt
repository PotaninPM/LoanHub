package com.potaninpm.feature_my_requests.utils

import androidx.annotation.StringRes

data class LoansFilter(
    val loan: String,
    @StringRes val labelResId: Int,
    val count: Int,
    val isSelected: Boolean
)