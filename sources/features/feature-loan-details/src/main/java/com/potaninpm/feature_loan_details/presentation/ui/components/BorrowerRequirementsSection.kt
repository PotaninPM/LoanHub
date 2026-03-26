package com.potaninpm.feature_loan_details.presentation.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.potaninpm.feature_loan_details.R
import com.potaninpm.feature_loan_details.presentation.components.DetailCard
import com.potaninpm.feature_loan_details.presentation.components.DetailInfoRow
import com.potaninpm.feature_loan_details.utils.BorrowerRequirements
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun BorrowerRequirementsSection(reqs: BorrowerRequirements) {
    DetailCard {
        LoanHubUiText(
            text = stringResource(R.string.title_borrower_requirements),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        DetailInfoRow(
            title = stringResource(R.string.format_age_range, reqs.minAge, reqs.maxAge),
            subtitle = stringResource(R.string.label_age)
        )

        DetailInfoRow(
            title = stringResource(if (reqs.citizenshipRequired) R.string.value_required else R.string.value_not_required),
            subtitle = stringResource(R.string.label_citizenship)
        )

        DetailInfoRow(
            title = stringResource(R.string.format_months_from, reqs.minWorkExperienceMonths),
            subtitle = stringResource(R.string.label_work_experience)
        )

        DetailInfoRow(
            title = stringResource(if (reqs.incomeConfirmationRequired) R.string.value_required else R.string.value_not_required),
            subtitle = stringResource(R.string.label_income_confirmation)
        )
    }
}
