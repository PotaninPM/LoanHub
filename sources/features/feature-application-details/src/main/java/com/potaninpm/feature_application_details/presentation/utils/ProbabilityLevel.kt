package com.potaninpm.feature_application_details.presentation.utils

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.potaninpm.feature_loan_details.R

enum class ProbabilityLevel(val color: Color, @StringRes val labelResId: Int) {
    HIGH(Color.Green, R.string.probability_high),
    MEDIUM(Color.Yellow, R.string.probability_medium),
    LOW(Color.Red, R.string.probability_low),
    UNKNOWN(Color.Gray, R.string.probability_unknown)
}