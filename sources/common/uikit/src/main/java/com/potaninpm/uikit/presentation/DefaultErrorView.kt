package com.potaninpm.uikit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.potaninpm.uikit.R
import com.potaninpm.uikit.presentation.buttons.LoanHubUiTinyButton
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun DefaultErrorView(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.default_error_title),
    description: String = stringResource(R.string.default_error_description),
    buttonText: String = stringResource(R.string.default_error_button_text),
    showButton: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoanHubUiText(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))

        LoanHubUiText(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (showButton) {
            Spacer(Modifier.height(24.dp))

            LoanHubUiTinyButton(
                text = buttonText,
                onClick = onRetry,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}