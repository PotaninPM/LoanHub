package com.potaninpm.feature_my_requests.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_my_requests.R
import com.potaninpm.feature_my_requests.domain.repository.LoanApplicationRepository
import com.potaninpm.feature_my_requests.domain.usecase.DeleteApplicationUseCase
import com.potaninpm.feature_my_requests.domain.usecase.GetMyApplicationsUseCase
import com.potaninpm.feature_my_requests.presentation.models.LoanCategory
import com.potaninpm.feature_my_requests.presentation.state.ExportFormat
import com.potaninpm.feature_my_requests.presentation.state.MyRequestsState
import com.potaninpm.feature_my_requests.utils.LoansFilter
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class MyRequestsViewModel(
    private val getMyApplicationsUseCase: GetMyApplicationsUseCase,
    private val deleteApplicationUseCase: DeleteApplicationUseCase,
    private val loanApplicationRepository: LoanApplicationRepository,
    private val isAdminFlow: Flow<Boolean>,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private var isAdmin: Boolean = false

    private val _state = MutableStateFlow(MyRequestsState(isAdmin = isAdmin))
    val state: StateFlow<MyRequestsState> = _state.asStateFlow()

    private var allApplications: List<LoanApplication> = emptyList()

    init {
        viewModelScope.launch {
            isAdminFlow.collectLatest { admin ->
                isAdmin = admin
                _state.update { it.copy(isAdmin = admin) }
                loadData()
            }
        }
    }

    fun loadData(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _state.update { it.copy(isRefreshing = true, error = null) }
            } else {
                _state.update { it.copy(isLoading = true, error = null) }
            }

            val appsResult = withContext(dispatcherProvider.io) {
                getMyApplicationsUseCase(isAdmin = isAdmin)
            }
            
            if (appsResult.isSuccess) {
                allApplications = appsResult.getOrThrow()
                applyFilters()
            } else {
                _state.update { it.copy(error = appsResult.exceptionOrNull()?.message) }
            }

            _state.update { it.copy(isLoading = false, isRefreshing = false) }
        }
    }

    fun refresh() {
        loadData(isRefresh = true)
    }

    private fun applyFilters() {
        val allDrafts = allApplications.filter { it.status == DRAFT }
        val submittedApps = allApplications.filter { it.status != DRAFT }
        val counts = submittedApps.groupingBy { it.type }.eachCount()
        val totalSubmittedCount = submittedApps.size

        var filteredSubmitted = submittedApps

        _state.value.filterType?.let { type ->
            filteredSubmitted = filteredSubmitted.filter { it.type == type.id }
        }

        val allFilter = LoansFilter(
            loan = ALL,
            labelResId = R.string.filter_all,
            count = totalSubmittedCount,
            isSelected = _state.value.filterType == null
        )

        val categoryFilters = LoanCategory.entries.map { category ->
            LoansFilter(
                loan = category.id,
                labelResId = category.labelResId,
                count = counts[category.id] ?: 0,
                isSelected = _state.value.filterType == category
            )
        }

        _state.update {
            it.copy(
                applications = allApplications,
                drafts = allDrafts,
                submittedApplications = filteredSubmitted,
                counts = counts,
                filters = listOf(allFilter) + categoryFilters,
                totalSubmittedCount = totalSubmittedCount
            )
        }
    }

    fun setFilter(type: LoanCategory?) {
        _state.update { it.copy(filterType = type) }
        applyFilters()
    }

    fun toggleDraftsExpanded() {
        _state.update { it.copy(isDraftsExpanded = !it.isDraftsExpanded) }
    }

    fun deleteApplication(id: String) {
        viewModelScope.launch {
            try {
                withContext(dispatcherProvider.io) {
                    deleteApplicationUseCase(id)
                }
                allApplications = allApplications.filterNot { it.id == id }
                applyFilters()
            } catch (_: Exception) {
            }
        }
    }

    fun approveApplication(id: String) = updateApplicationStatus(id = id, status = STATUS_APPROVED)

    fun rejectApplication(id: String) = updateApplicationStatus(id = id, status = STATUS_REJECTED)

    private fun updateApplicationStatus(id: String, status: String) {
        viewModelScope.launch {
            _state.update { it.copy(updatingStatusIds = it.updatingStatusIds + id) }
            try {
                withContext(dispatcherProvider.io) {
                    loanApplicationRepository.updateApplicationStatus(id, status)
                }
                loadData()
            } catch (_: Exception) {
            }
            _state.update { it.copy(updatingStatusIds = it.updatingStatusIds - id) }
        }
    }

    fun importApplicationAsDraft(json: String, onNavigate: (loanType: String, fieldsJson: String) -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isImporting = true, importError = null) }
            try {
                val (loanType, fieldsJson) = withContext(dispatcherProvider.io) {
                    val apps = Json.decodeFromString<List<LoanApplication>>(json)
                    val first = apps.firstOrNull() ?: throw Exception("Imported file is empty or invalid")
                    val fieldsJsonString = Json.encodeToString(first.fields)
                    first.type to fieldsJsonString
                }
                onNavigate(loanType, fieldsJson)
            } catch (e: Exception) {
                _state.update { it.copy(importError = e.message ?: "Unknown JSON error") }
            } finally {
                _state.update { it.copy(isImporting = false) }
            }
        }
    }

    fun importApplicationCsvAsDraft(csv: String, onNavigate: (loanType: String, fieldsJson: String) -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isImporting = true, importError = null) }
            try {
                val (type, fieldsJson) = withContext(dispatcherProvider.io) {
                    val lines = csv.lines().filter { it.isNotBlank() }
                    if (lines.size <= 1) throw Exception("CSV file is empty or missing data")
                    val line = lines[1]
                    val cols = line.split(",", limit = 5)
                    if (cols.size < 4) throw Exception("Invalid CSV format: missing columns")
                    val type = cols[0].trim()
                    val fieldsJsonString = if (cols.size > 4) {
                        cols[4].trim()
                    } else "[]"
                    if (type.isEmpty()) throw Exception("Invalid CSV: missing loan type")
                    type to fieldsJsonString
                }
                
                onNavigate(type, fieldsJson)
            } catch (e: Exception) {
                _state.update { it.copy(importError = e.message ?: "Unknown CSV error") }
            } finally {
                _state.update { it.copy(isImporting = false) }
            }
        }
    }

    fun clearImportError() {
        _state.update { it.copy(importError = null) }
    }

    fun showExportDialog(format: ExportFormat) {
        _state.update {
            it.copy(
                showExportDialog = true,
                exportFormat = format,
                selectedExportId = it.submittedApplications.firstOrNull()?.id
            )
        }
    }

    fun dismissExportDialog() {
        _state.update { it.copy(showExportDialog = false, exportFormat = null, selectedExportId = null) }
    }

    fun toggleExportSelection(id: String) {
        _state.update { it.copy(selectedExportId = id) }
    }

    fun exportSelectedJson(): String {
        val selected = allApplications.find { it.id == _state.value.selectedExportId } ?: return "[]"
        return try {
            Json.encodeToString(listOf(selected))
        } catch (_: Exception) {
            "[]"
        }
    }

    fun exportSelectedCsv(): String {
        val selected = allApplications.find { it.id == _state.value.selectedExportId } ?: return ""
        val sb = StringBuilder()
        sb.appendLine("type,date,status,incomeCurrency,fields")
        val fieldsJson = try {
            Json.encodeToString(selected.fields)
        } catch (_: Exception) { "[]" }
        sb.appendLine("${selected.type},${selected.date},${selected.status},${selected.incomeCurrency},$fieldsJson")
        return sb.toString()
    }

    private companion object {
        const val STATUS_APPROVED = "APPROVED"
        const val STATUS_REJECTED = "REJECTED"
        const val DRAFT = "DRAFT"
        const val ALL = "all"
    }
}
