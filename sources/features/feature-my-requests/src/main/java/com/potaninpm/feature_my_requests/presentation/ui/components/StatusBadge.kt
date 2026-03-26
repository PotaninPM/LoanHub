package com.potaninpm.feature_my_requests.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.potaninpm.feature_my_requests.R
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun StatusBadge(status: String) {
    val (text, color, containerColor) = when (status) {
        "DRAFT" -> Triple(
            stringResource(R.string.status_draft),
            MaterialTheme.colorScheme.onSurfaceVariant,
            MaterialTheme.colorScheme.surfaceVariant
        )
        "SUBMITTED" -> Triple(
            stringResource(R.string.status_submitted),
            MaterialTheme.colorScheme.onPrimaryContainer,
            MaterialTheme.colorScheme.primaryContainer
        )
        "APPROVED" -> Triple(
            stringResource(R.string.status_approved),
            MaterialTheme.colorScheme.onTertiaryContainer,
            Color(18, 166, 0, 255).copy(alpha = 0.8f)
        )
        "REJECTED" -> Triple(
            stringResource(R.string.status_rejected),
            MaterialTheme.colorScheme.onErrorContainer,
            MaterialTheme.colorScheme.errorContainer
        )
        else -> Triple(
            status,
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.colorScheme.surface
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(containerColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        LoanHubUiText(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}