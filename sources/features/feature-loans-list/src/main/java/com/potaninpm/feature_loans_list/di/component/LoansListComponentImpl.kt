package com.potaninpm.feature_loans_list.di.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_loans_list.data.repository.LoanProductsRepositoryImpl
import com.potaninpm.feature_loans_list.di.LoansListDeps
import com.potaninpm.feature_loans_list.domain.usecase.GetAllProductsUseCase
import com.potaninpm.feature_loans_list.domain.usecase.GetPopularOffersUseCase
import com.potaninpm.feature_loans_list.presentation.viewmodel.LoansListViewModel

class LoansListComponentImpl(
    private val deps: LoansListDeps
) : LoansListComponent {

    private val repository by lazy {
        LoanProductsRepositoryImpl(deps.loansSupabaseApi)
    }

    private val getAllProductsUseCase by lazy {
        GetAllProductsUseCase(repository)
    }

    private val getPopularOffersUseCase by lazy {
        GetPopularOffersUseCase(repository)
    }

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoansListViewModel(
                    getAllProductsUseCase,
                    getPopularOffersUseCase,
                    deps.dispatcherProvider
                ) as T
            }
        }
    }
}