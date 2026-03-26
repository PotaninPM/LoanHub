package com.potaninpm.feature_loan_application.di.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_loan_application.data.repository.LoanApplicationProfileRepositoryImpl
import com.potaninpm.feature_loan_application.data.repository.SuggestionRepositoryImpl
import com.potaninpm.feature_loan_application.di.LoanApplicationDeps
import com.potaninpm.feature_loan_application.domain.factory.LoanFormFactory
import com.potaninpm.feature_loan_application.domain.usecase.GetSuggestionUseCase
import com.potaninpm.feature_loan_application.domain.validation.LoanFieldValidator
import com.potaninpm.feature_loan_application.presentation.viewmodel.LoanApplicationViewModel

class LoanApplicationComponentImpl(
    private val deps: LoanApplicationDeps
) : LoanApplicationComponent {

    private val suggestionRepository by lazy {
        SuggestionRepositoryImpl(deps.dadataApi)
    }

    private val getSuggestionUseCase by lazy {
        GetSuggestionUseCase(suggestionRepository)
    }

    private val profileRepository by lazy {
        LoanApplicationProfileRepositoryImpl(deps.profileSupabaseApi)
    }

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoanApplicationViewModel(
                    getSuggestionUseCase = getSuggestionUseCase,
                    loanDraftsRepository = deps.loanDraftsRepository,
                    profileRepository = profileRepository,
                    formFactory = LoanFormFactory(),
                    validator = LoanFieldValidator(),
                    dispatcherProvider = deps.dispatcherProvider
                ) as T
            }
        }
    }
}