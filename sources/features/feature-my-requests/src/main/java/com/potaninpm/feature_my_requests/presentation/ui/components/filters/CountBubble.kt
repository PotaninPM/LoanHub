package com.potaninpm.feature_my_requests.presentation.ui.components.filters

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun CountBubble(
    count: Int,
    isSelected: Boolean,
) {
    val scale = remember { Animatable(1f) }

    LaunchedEffect(isSelected) {
        if (isSelected) {
            scale.snapTo(1f)
            scale.animateTo(
                1.15f,
                animationSpec = tween(
                    durationMillis = 140,
                    easing = FastOutSlowInEasing
                )
            )
            scale.animateTo(
                1f,
                animationSpec = tween(
                    durationMillis = 140,
                    easing = FastOutSlowInEasing
                )
            )
        } else {
            scale.animateTo(
                1f,
                animationSpec = tween(durationMillis = 120)
            )
        }
    }

    Box(
        modifier = Modifier
            .graphicsLayer(
                scaleX = scale.value,
                scaleY = scale.value
            )
            .size(24.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.2f)
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
            ),
        contentAlignment = Alignment.Center
    ) {
        LoanHubUiText(
            text = count.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondary
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}