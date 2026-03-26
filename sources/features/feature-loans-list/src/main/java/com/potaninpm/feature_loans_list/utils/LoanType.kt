package com.potaninpm.feature_loans_list.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class LoanType(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val image: Int?,
    val gradientStart: Color,
    val gradientEnd: Color
)