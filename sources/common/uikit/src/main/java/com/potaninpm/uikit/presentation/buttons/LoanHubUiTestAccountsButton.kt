package com.potaninpm.uikit.presentation.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.potaninpm.uikit.R
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun LoanHubUiTestAccountsButton(
    onClick: () -> Unit
) {
    val colorsList = if (isSystemInDarkTheme()) {
        listOf(
            MaterialTheme.colorScheme.surface,
            Color(0, 64, 47)
        )
    } else {
        listOf(
            Color(1, 72, 53, 255),
            MaterialTheme.colorScheme.surface,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .background(
                Brush.horizontalGradient(colorsList)
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.onSurface.copy(0.05f),
                RoundedCornerShape(20.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.lotty),
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        LoanHubUiText(
            text = stringResource(R.string.test_acccounts),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Composable
private fun LoanHubUiTestAccountsButtonDarkPreview() {
    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        LoanHubUiTestAccountsButton({})
    }
}

@Preview
@Composable
private fun LoanHubUiTestAccountsButtonLightPreview() {
    MaterialTheme(
        colorScheme = lightColorScheme()
    ) {
        LoanHubUiTestAccountsButton({})
    }
}