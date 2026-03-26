package com.potaninpm.feature_application_details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_application_details.domain.repository.LoanDetailsRepository
import com.potaninpm.network.repository.CurrencyRepository
import com.potaninpm.utils.dispatchers.DispatcherProvider

class DetailsViewModelFactory(
    private val id: String,
    private val repository: LoanDetailsRepository,
    private val currencyRepository: CurrencyRepository,
    private val isAdmin: Boolean,
    private val dispatcherProvider: DispatcherProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplicationDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ApplicationDetailsViewModel(id, repository, currencyRepository, isAdmin, dispatcherProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}