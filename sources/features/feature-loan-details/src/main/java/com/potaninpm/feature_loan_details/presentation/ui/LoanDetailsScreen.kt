package com.potaninpm.feature_loan_details.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.feature_loan_details.R
import com.potaninpm.feature_loan_details.di.LoanDetailsComponentHolder
import com.potaninpm.feature_loan_details.presentation.ui.components.LoanDetailsSkeleton
import com.potaninpm.feature_loan_details.presentation.ui.components.OfferDetailsBottomView
import com.potaninpm.feature_loan_details.presentation.ui.components.OfferDetailsHeaderView
import com.potaninpm.feature_loan_details.presentation.viewmodel.LoanDetailsViewModel
import com.potaninpm.uikit.presentation.DefaultErrorView
import com.potaninpm.uikit.presentation.buttons.LoanHubUiButton
import com.potaninpm.uikit.presentation.ext.debounced
import com.potaninpm.uikit.utils.parseHexColorOrNull

@Composable
fun LoanDetailsScreen(
    loanId: String,
    startColor: String,
    endColor: String,
    onApplyClick: () -> Unit,
    onBack: () -> Unit
) {
    val component = LoanDetailsComponentHolder.get()

    val viewModel: LoanDetailsViewModel = viewModel(
        factory = component.provideViewModelFactory(loanId)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    val loan = state.loanDetails

    when {
        state.isLoading -> {
            LoanDetailsSkeleton(modifier = Modifier.fillMaxSize())
        }
        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                DefaultErrorView(
                    onRetry = { viewModel.loadDetails() }
                )
            }
        }
        loan != null -> {
            val start = parseHexColorOrNull(startColor) ?: MaterialTheme.colorScheme.primary
            val end = parseHexColorOrNull(endColor) ?: MaterialTheme.colorScheme.secondary

            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(start, end)
                        )
                    )
            ) {
                OfferDetailsHeaderView(
                    headerRes = null,
                    title = loan.title
                )

                Column(modifier = Modifier.fillMaxSize()) {
                    IconButton(
                        modifier = Modifier
                            .zIndex(1f)
                            .statusBarsPadding()
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)),
                        onClick = onBack.debounced()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }

                    OfferDetailsBottomView(
                        loan = loan,
                        modifier = Modifier
                            .weight(1f)
                    )
                }


                LoanHubUiButton(
                    text = stringResource(R.string.make_application),
                    contentColor = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    onClick = onApplyClick
                )
            }
        }
    }
}
