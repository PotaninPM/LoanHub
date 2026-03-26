package com.potaninpm.feature_my_requests.presentation.ui.components.filters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.potaninpm.feature_my_requests.utils.LoansFilter

@Composable
fun LoanFilters(
    filters: List<LoansFilter>,
    selectedFilterType: String?,
    totalCount: Int,
    isLoading: Boolean,
    onFilterClick: (String?) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (!isLoading) {
            items(filters, key = { it.loan }) { filter ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInHorizontally(initialOffsetX = { it / 2 }),
                    exit = fadeOut()
                ) {
                    CategoryChip(
                        text = stringResource(filter.labelResId),
                        count = filter.count,
                        isSelected = filter.isSelected,
                        onClick = { onFilterClick(filter.loan) }
                    )
                }
            }
        }
    }
}
