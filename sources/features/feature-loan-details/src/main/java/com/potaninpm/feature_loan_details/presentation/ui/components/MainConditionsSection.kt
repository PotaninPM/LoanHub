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
import com.potaninpm.feature_loan_details.utils.MainConditions
import com.potaninpm.feature_loan_details.utils.formatAmount
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun MainConditionsSection(conditions: MainConditions) {
    DetailCard {
        LoanHubUiText(
            text = stringResource(R.string.title_main_conditions),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))

        DetailInfoRow(
            title = stringResource(
                R.string.format_amount_range,
                formatAmount(conditions.minAmount),
                formatAmount(conditions.maxAmount)
            ),
            subtitle = stringResource(R.string.label_loan_amount)
        )

        if (conditions.minTermInMonths != null && conditions.maxTermInMonths != null) {
            DetailInfoRow(
                title = stringResource(
                    R.string.format_term_months,
                    conditions.minTermInMonths,
                    conditions.maxTermInMonths
                ),
                subtitle = stringResource(R.string.label_loan_term)
            )
        } else {
            DetailInfoRow(
                title = stringResource(R.string.value_term_indefinite),
                subtitle = stringResource(R.string.label_contract_term)
            )
        }

        DetailInfoRow(
            title = stringResource(R.string.format_percent_from, conditions.interestRateFrom),
            subtitle = stringResource(R.string.label_interest_rate),
            emphasize = true
        )

        conditions.initialPaymentPercentFrom?.let {
            DetailInfoRow(
                title = stringResource(R.string.format_percent_from, it),
                subtitle = stringResource(R.string.label_initial_payment)
            )
        }

        conditions.gracePeriodDays?.let {
            DetailInfoRow(
                title = stringResource(R.string.format_days_up_to, it),
                subtitle = stringResource(R.string.label_grace_period)
            )
        }

        conditions.minMonthlyPaymentPercent?.let {
            DetailInfoRow(
                title = stringResource(R.string.format_min_payment_percent, it),
                subtitle = stringResource(R.string.label_min_payment)
            )
        }
    }
}
