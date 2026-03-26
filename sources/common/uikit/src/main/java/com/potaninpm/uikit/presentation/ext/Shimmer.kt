package com.potaninpm.uikit.presentation.ext

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect

@Composable
fun Modifier.shimmerLoading(
    shimmerColors: List<Color> = listOf(
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.2f)
    ),
    durationMillis: Int = 3000
): Modifier {
    val transition = rememberInfiniteTransition(label = "shimmer")

    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 3000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "translateAnim"
    )

    return this.drawWithCache {
        val width = size.width
        val height = size.height

        val gradientWidth = 0.7f * width
        val startX = translateAnim - gradientWidth
        val endX = translateAnim

        val brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = startX, y = 0f),
            end = Offset(x = endX, y = height)
        )

        onDrawBehind {
            clipRect {
                drawRect(brush = brush)
            }
        }
    }
}
