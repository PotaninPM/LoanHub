package com.potaninpm.feature_my_requests.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_my_requests.R
import com.potaninpm.feature_my_requests.presentation.ui.components.filters.LoanFilters
import com.potaninpm.feature_my_requests.utils.LoansFilter
import com.potaninpm.feature_my_requests.utils.MyRequestsScreenTags
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun SubmittedApplicationsList(
    submittedApplications: List<LoanApplication>,
    filters: List<LoansFilter>,
    selectedFilterType: String?,
    totalSubmittedCount: Int,
    isLoading: Boolean,
    isAdmin: Boolean,
    updatingStatusIds: Set<String>,
    onFilterClick: (String?) -> Unit,
    onApplicationClick: (id: String) -> Unit,
    onApprove: (id: String) -> Unit,
    onReject: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))

        LoanFilters(
            filters = filters,
            selectedFilterType = selectedFilterType,
            totalCount = totalSubmittedCount,
            isLoading = isLoading,
            onFilterClick = onFilterClick
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoanHubUiText(
                text = stringResource(R.string.submitted_requests_count, submittedApplications.size),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag(MyRequestsScreenTags.SUBMITTED_LIST),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (submittedApplications.isEmpty() && !isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        EmptyState()
                    }
                }
            } else {
                items(submittedApplications, key = { it.id }) { app ->
                    ApplicationCard(
                        app = app,
                        onClick = { onApplicationClick(app.id) },
                        isAdmin = isAdmin,
                        isUpdatingStatus = app.id in updatingStatusIds,
                        onApprove = { onApprove(app.id) },
                        onReject = { onReject(app.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}
