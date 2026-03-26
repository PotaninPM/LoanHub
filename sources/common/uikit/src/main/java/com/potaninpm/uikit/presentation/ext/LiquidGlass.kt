package com.potaninpm.uikit.presentation.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.liquidGlass(
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 2.dp,
    alpha: Float = 0.08f
): Modifier {
    val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
    val primaryColor = MaterialTheme.colorScheme.primary

    return this
        .shadow(elevation, RoundedCornerShape(cornerRadius))
        .clip(RoundedCornerShape(cornerRadius))
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    surfaceColor.copy(alpha = alpha + 0.04f),
                    primaryColor.copy(alpha = alpha * 0.5f),
                    surfaceColor.copy(alpha = alpha)
                )
            )
        )
        .background(MaterialTheme.colorScheme.surface)
}
