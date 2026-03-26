package com.potaninpm.feature_my_requests.presentation.state

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_my_requests.presentation.models.LoanCategory

import com.potaninpm.feature_my_requests.utils.LoansFilter

data class MyRequestsState(
    val applications: List<LoanApplication> = emptyList(),
    val drafts: List<LoanApplication> = emptyList(),
    val submittedApplications: List<LoanApplication> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val filterType: LoanCategory? = null,
    val filters: List<LoansFilter> = emptyList(),
    val counts: Map<String, Int> = emptyMap(),
    val error: String? = null,
    val totalSubmittedCount: Int = 0,
    val isDraftsExpanded: Boolean = true,
    val isExporting: Boolean = false,
    val isImporting: Boolean = false,
    val isAdmin: Boolean = false,
    val updatingStatusIds: Set<String> = emptySet(),
    val showExportDialog: Boolean = false,
    val exportFormat: ExportFormat? = null,
    val selectedExportId: String? = null,
    val importError: String? = null
)

enum class ExportFormat {
    JSON, CSV
}
