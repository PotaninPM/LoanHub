package com.potaninpm.feature_application_details.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.potaninpm.feature_application_details.presentation.utils.ProbabilityLevel
import com.potaninpm.feature_loan_details.R
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun ProbabilityCard(
    probability: ProbabilityLevel,
    payment: Int
) {
    val bgColor = when (probability) {
        ProbabilityLevel.HIGH -> Color(0xFF10B981).copy(alpha = 0.12f)
        ProbabilityLevel.MEDIUM -> Color(0xFFF59E0B).copy(alpha = 0.12f)
        ProbabilityLevel.LOW -> Color(0xFFEF4444).copy(alpha = 0.12f)
        ProbabilityLevel.UNKNOWN -> MaterialTheme.colorScheme.surfaceVariant
    }

    val textColor = when (probability) {
        ProbabilityLevel.HIGH -> Color(0xFF059669)
        ProbabilityLevel.MEDIUM -> Color(0xFFD97706)
        ProbabilityLevel.LOW -> Color(0xFFDC2626)
        ProbabilityLevel.UNKNOWN -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LoanHubUiText(
                text = stringResource(R.string.probability_title),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            LoanHubUiText(
                text = stringResource(probability.labelResId),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            LoanHubUiText(
                text = stringResource(R.string.monthly_payment_format, payment),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
