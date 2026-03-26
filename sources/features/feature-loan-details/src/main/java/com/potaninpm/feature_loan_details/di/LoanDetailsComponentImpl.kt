package com.potaninpm.feature_loan_details.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_loan_details.data.repository.LoanDetailsRepositoryImpl
import com.potaninpm.feature_loan_details.domain.usecase.GetLoanDetailsUseCase
import com.potaninpm.feature_loan_details.presentation.viewmodel.LoanDetailsViewModel

class LoanDetailsComponentImpl(
    private val deps: LoanDetailsDeps
) : LoanDetailsComponent {

    private val repository by lazy {
        LoanDetailsRepositoryImpl(deps.loansSupabaseApi)
    }

    private val getLoanDetailsUseCase by lazy {
        GetLoanDetailsUseCase(repository)
    }

    override fun provideViewModelFactory(loanId: String): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoanDetailsViewModel(loanId, getLoanDetailsUseCase, deps.dispatcherProvider) as T
            }
        }
    }
}