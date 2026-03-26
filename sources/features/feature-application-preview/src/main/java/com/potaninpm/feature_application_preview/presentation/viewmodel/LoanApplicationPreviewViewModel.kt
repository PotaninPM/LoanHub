package com.potaninpm.feature_application_preview.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potaninpm.database.models.FormField
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_application_preview.domain.usecase.SubmitLoanUseCase
import com.potaninpm.feature_application_preview.presentation.state.PreviewState
import com.potaninpm.network.repository.CurrencyRepository
import com.potaninpm.uikit.presentation.details.DetailItem
import com.potaninpm.uikit.utils.LoanDetailsUiMapper
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor

class LoanApplicationPreviewViewModel(
    private val initialFields: List<FormField>,
    private val incomeCurrency: String,
    private val amountCurrency: String,
    private val loanType: String,
    private val submitLoanUseCase: SubmitLoanUseCase,
    private val currencyRepository: CurrencyRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private val uiMapper = LoanDetailsUiMapper()

    private val _state = MutableStateFlow(
        PreviewState(
            fields = initialFields,
            incomeCurrency = incomeCurrency,
            amountCurrency = amountCurrency
        )
    )
    val state: StateFlow<PreviewState> = _state.asStateFlow()

    init {
        convertCurrencies()
    }

    private fun convertCurrencies() {
        if (incomeCurrency == RUB && amountCurrency == RUB) {
            val income = initialFields.find { it.id == INCOME }?.value?.toIntOrNull()
            val amount = initialFields.find { it.id == AMOUNT }?.value?.toIntOrNull()
            
            val items = getDetailItems(income, amount)
            _state.update { it.copy(convertedIncome = income, convertedAmount = amount, detailItems = items) }
            return
        }

        viewModelScope.launch(dispatcherProvider.io) {
            _state.update { it.copy(isLoading = true) }

            val incomeVal = initialFields.find { it.id == INCOME }?.value?.toDoubleOrNull() ?: 0.0
            val amountVal = initialFields.find { it.id == AMOUNT }?.value?.toDoubleOrNull() ?: 0.0

            var convertedIncome: Int? = null
            var convertedAmount: Int? = null

            if (incomeCurrency == RUB) {
                convertedIncome = incomeVal.toInt()
            } else {
                currencyRepository.convertCurrency(incomeVal, incomeCurrency, RUB)
                    .onSuccess { convertedIncome = floor(it).toInt() }
            }

            if (amountCurrency == RUB) {
                convertedAmount = amountVal.toInt()
            } else {
                currencyRepository.convertCurrency(amountVal, amountCurrency, RUB)
                    .onSuccess { convertedAmount = ceil(it).toInt() }
            }

            val items = getDetailItems(convertedIncome, convertedAmount)

            _state.update {
                it.copy(
                    convertedIncome = convertedIncome,
                    convertedAmount = convertedAmount,
                    detailItems = items,
                    isLoading = false
                )
            }
        }
    }

    private fun getDetailItems(convertedIncome: Int?, convertedAmount: Int?): List<DetailItem> {
        val app = LoanApplication(
            id = "",
            type = loanType,
            date = java.time.LocalDate.now().toString(),
            fields = initialFields,
            incomeCurrency = incomeCurrency,
            amountCurrency = amountCurrency
        )
        return uiMapper.mapToDetailItems(app, convertedIncome, convertedAmount)
    }

    fun submit(onSuccess: () -> Unit) {
        if (_state.value.isLoading) return
        
        viewModelScope.launch(dispatcherProvider.io) {
            _state.update { it.copy(isLoading = true) }
            val result = submitLoanUseCase.invoke(
                LoanApplication(
                    id = "",
                    type = loanType,
                    date = java.time.LocalDate.now().toString(),
                    fields = initialFields,
                    incomeCurrency = incomeCurrency,
                    amountCurrency = amountCurrency
                )
            )
            if (result.isSuccess) {
                onSuccess()
            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private companion object {
        const val INCOME = "income"
        const val AMOUNT = "amount"
        const val RUB = "RUB"
    }
}
