package com.potaninpm.uikit.presentation.theme.configs

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.potaninpm.uikit.R

internal val CustomTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_bold)),
        fontSize = 38.sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_bold)),
        fontSize = 34.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_bold)),
        fontSize = 26.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_medium)),
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_medium)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_medium)),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_regular)),
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_regular)),
        fontSize = 18.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.tinkoff_sans_regular)),
        fontSize = 14.sp
    ),
)