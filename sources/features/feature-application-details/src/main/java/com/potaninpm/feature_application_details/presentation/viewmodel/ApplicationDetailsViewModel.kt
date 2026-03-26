package com.potaninpm.feature_application_details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_application_details.domain.logic.ProbabilityCalculator
import com.potaninpm.feature_application_details.domain.repository.LoanDetailsRepository
import com.potaninpm.feature_application_details.presentation.state.DetailsState
import com.potaninpm.network.repository.CurrencyRepository
import com.potaninpm.uikit.utils.LoanDetailsUiMapper
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.ceil
import kotlin.math.floor

class ApplicationDetailsViewModel(
    private val applicationId: String,
    private val repository: LoanDetailsRepository,
    private val currencyRepository: CurrencyRepository,
    private val isAdmin: Boolean = false,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private val probabilityCalculator = ProbabilityCalculator()
    private val uiMapper = LoanDetailsUiMapper()

    private val _state = MutableStateFlow(DetailsState(isAdmin = isAdmin))
    val state: StateFlow<DetailsState> = _state

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = withContext(dispatcherProvider.io) {
                repository.getApplication(applicationId)
            }

            result.fold(
                onSuccess = { app ->
                    if (app == null) {
                        _state.update { it.copy(isLoading = false) }
                        return@launch
                    }

                    val (others, rubData) = withContext(dispatcherProvider.io) {
                        val othersList = repository.getApplicationsByType(app.type)
                            .getOrDefault(emptyList())
                            .filter { it.id != app.id }
                        val converted = convertToRub(app)
                        othersList to converted
                    }
                    
                    val (finalIncome, finalAmount) = rubData
                    val termVal = app.fields.find { it.id == TERM }?.value?.toDoubleOrNull() ?: 0.0
                    val (prob, _, payment) = probabilityCalculator.calculate(
                        app.type, finalIncome.toDouble(), finalAmount.toDouble(), termVal
                    )
                    val items = uiMapper.mapToDetailItems(app, finalIncome, finalAmount)

                    _state.update {
                        it.copy(
                            application = app,
                            probability = prob,
                            payment = payment,
                            similarApplications = others,
                            convertedIncome = finalIncome,
                            convertedAmount = finalAmount,
                            detailItems = items,
                            isLoading = false
                        )
                    }
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
                }
            )
        }
    }

    fun deleteApplication(onDeleted: () -> Unit) {
        viewModelScope.launch {
            val result = withContext(dispatcherProvider.io) {
                repository.deleteApplication(applicationId)
            }
            result.fold(
                onSuccess = { onDeleted() },
                onFailure = { e ->
                    _state.update { it.copy(error = e.message) }
                }
            )
        }
    }

    private suspend fun convertToRub(app: LoanApplication): Pair<Int, Int> {
        val incomeVal = app.fields.find { it.id == INCOME }?.value?.toDoubleOrNull() ?: 0.0
        val amountVal = app.fields.find { it.id == AMOUNT }?.value?.toDoubleOrNull() ?: 0.0

        var convertedIncome = if (app.incomeCurrency == RUB) incomeVal.toInt() else null
        var convertedAmount = if (app.amountCurrency == RUB) amountVal.toInt() else null

        if (convertedIncome == null) {
            currencyRepository.convertCurrency(incomeVal, app.incomeCurrency, RUB)
                .onSuccess { convertedIncome = floor(it).toInt() }
        }

        if (convertedAmount == null) {
            currencyRepository.convertCurrency(amountVal, app.amountCurrency, RUB)
                .onSuccess { convertedAmount = ceil(it).toInt() }
        }

        return (convertedIncome ?: incomeVal.toInt()) to (convertedAmount ?: amountVal.toInt())
    }

    private companion object {
        const val INCOME = "income"
        const val AMOUNT = "amount"
        const val RUB = "RUB"
        const val TERM = "term"
    }
}
