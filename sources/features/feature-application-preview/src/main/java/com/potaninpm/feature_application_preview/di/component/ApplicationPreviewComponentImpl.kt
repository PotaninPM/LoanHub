package com.potaninpm.feature_application_preview.di.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.database.models.FormField
import com.potaninpm.feature_application_preview.data.repository.SubmitApplicationRepositoryImpl
import com.potaninpm.feature_application_preview.di.ApplicationPreviewDeps
import com.potaninpm.feature_application_preview.domain.repository.SubmitApplicationRepository
import com.potaninpm.feature_application_preview.domain.usecase.SubmitLoanUseCase
import com.potaninpm.feature_application_preview.presentation.viewmodel.LoanApplicationPreviewViewModel

class ApplicationPreviewComponentImpl(
    private val deps: ApplicationPreviewDeps
) : ApplicationPreviewComponent {
    private val submitLoanUseCase: SubmitLoanUseCase by lazy {
        SubmitLoanUseCase(submitApplicationRepository)
    }

    private val submitApplicationRepository: SubmitApplicationRepository by lazy {
        SubmitApplicationRepositoryImpl(deps.applicationsSupabaseApi)
    }

    override fun provideViewModelFactory(
        initialFields: List<FormField>,
        incomeCurrency: String,
        amountCurrency: String,
        loanType: String
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoanApplicationPreviewViewModel(
                    submitLoanUseCase = submitLoanUseCase,
                    initialFields = initialFields,
                    incomeCurrency = incomeCurrency,
                    amountCurrency = amountCurrency,
                    loanType = loanType,
                    currencyRepository = deps.currencyRepository,
                    dispatcherProvider = deps.dispatcherProvider
                ) as T
            }
        }
    }
}
