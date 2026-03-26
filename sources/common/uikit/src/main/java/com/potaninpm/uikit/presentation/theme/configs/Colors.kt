package com.potaninpm.uikit.presentation.theme.configs

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val CustomLightColors = lightColorScheme(
    background = Color(0xFFECECEC),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE0E0E0),
    onBackground = Color(0xFF1A1A1A),
    onSurface = Color(0xFF1A1A1A),
    onSurfaceVariant = Color(0xFF7A7A7A),

    primary = Color(0xFF458CF6),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF458CF6),
    onSecondary = Color(0xFFFEFEFE),
    error = Color(0xFFFF5757),
    onSecondaryFixed = Color(0, 64, 47)
)

internal val CustomDarkColors = darkColorScheme(
    background = Color(0xFF121212),
    surface = Color(0xFF212121),
    surfaceVariant = Color(0xFF2A2A2A),
    onBackground = Color(0xFFFEFEFE),
    onSurface = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xFF949494),
    primary = Color(0xFF458CF6),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFF458CF6),
    onSecondary = Color(0xFFFEFEFE),
    error = Color(0xFFFF5757),
    onSecondaryFixed = Color(0, 64, 47)
)
