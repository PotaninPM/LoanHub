package com.potaninpm.feature_loans_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potaninpm.feature_loans_list.domain.usecase.GetAllProductsUseCase
import com.potaninpm.feature_loans_list.domain.usecase.GetPopularOffersUseCase
import com.potaninpm.feature_loans_list.presentation.state.LoansListState
import com.potaninpm.feature_loans_list.presentation.utils.DEFAULT_LOAN_TYPES
import com.potaninpm.feature_loans_list.presentation.utils.exception.LoanListException
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoansListViewModel(
    private val getAllProducts: GetAllProductsUseCase,
    private val getPopularOffers: GetPopularOffersUseCase,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private val _state = MutableStateFlow(LoansListState(isLoading = true))
    val state: StateFlow<LoansListState> = _state.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts(isRefresh: Boolean = false) {
        if (isRefresh && (_state.value.isLoading || _state.value.isRefreshing)) return

        viewModelScope.launch(dispatcherProvider.io) {
            if (isRefresh) {
                _state.update { it.copy(isRefreshing = true, error = null) }
            } else {
                _state.update { it.copy(isLoading = true, error = null) }
            }

            val popularResult = getPopularOffers()
            val allResult = getAllProducts()

            val popular = popularResult.getOrDefault(_state.value.popularOffers.ifEmpty { DEFAULT_LOAN_TYPES })
            val all = allResult.getOrDefault(_state.value.allProducts.ifEmpty { DEFAULT_LOAN_TYPES })

            val error = when {
                popularResult.isFailure -> LoanListException(
                    message = popularResult.exceptionOrNull()?.message ?: "error",
                    exception = popularResult.exceptionOrNull()
                )
                allResult.isFailure -> LoanListException(
                    message = allResult.exceptionOrNull()?.message ?: "error",
                    exception = allResult.exceptionOrNull()
                )
                else -> null
            }

            _state.update {
                it.copy(
                    popularOffers = popular,
                    allProducts = all,
                    isLoading = false,
                    isRefreshing = false,
                    error = error
                )
            }
        }
    }

    fun refresh() {
        loadProducts(isRefresh = true)
    }
}
