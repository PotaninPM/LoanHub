package com.potaninpm.feature_loans_list.presentation.state

import com.potaninpm.feature_loans_list.presentation.utils.exception.LoanListException
import com.potaninpm.feature_loans_list.utils.LoanType

data class LoansListState(
    val popularOffers: List<LoanType> = emptyList(),
    val allProducts: List<LoanType> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: LoanListException? = null
)