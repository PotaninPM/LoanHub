package com.potaninpm.uikit.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.potaninpm.uikit.presentation.theme.configs.CustomDarkColors
import com.potaninpm.uikit.presentation.theme.configs.CustomLightColors
import com.potaninpm.uikit.presentation.theme.configs.CustomTypography

@Composable
fun LoanHubUiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CustomDarkColors else CustomLightColors

    MaterialTheme(
        typography = CustomTypography,
        colorScheme = colorScheme,
        content = content
    )
}