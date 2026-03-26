package com.potaninpm.feature_loan_details.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.potaninpm.feature_loan_details.presentation.components.DetailCard
import com.potaninpm.feature_loan_details.utils.LoanDetails
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun OfferDetailsBottomView(
    loan: LoanDetails,
    modifier: Modifier = Modifier
) {
    val conditions = loan.mainConditions
    val reqs = loan.borrowerRequirements

    Column(
        modifier
            .fillMaxWidth()
            .clip(
                AbsoluteRoundedCornerShape(
                    topLeft = 32.dp,
                    topRight = 32.dp
                )
            )
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DetailCard {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LoanHubUiText(
                    text = loan.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                LoanHubUiText(
                    text = loan.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        MainConditionsSection(conditions)
        BorrowerRequirementsSection(reqs)
    }
}
