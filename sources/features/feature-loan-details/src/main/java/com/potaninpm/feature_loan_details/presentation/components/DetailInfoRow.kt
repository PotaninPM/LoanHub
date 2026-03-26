package com.potaninpm.feature_loan_details.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun DetailInfoRow(
    title: String,
    subtitle: String,
    emphasize: Boolean = false,
    icon: (@Composable () -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        if (icon != null) {
            icon()
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            LoanHubUiText(
                text = title,
                style = if (emphasize) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = if(emphasize) FontWeight.SemiBold else FontWeight.Normal
            )
            if (subtitle.isNotEmpty()) {
                LoanHubUiText(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}