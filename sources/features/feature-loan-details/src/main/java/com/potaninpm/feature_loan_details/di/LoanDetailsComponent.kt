package com.potaninpm.feature_loan_details.di

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent

interface LoanDetailsComponent : DIComponent {
    fun provideViewModelFactory(loanId: String): ViewModelProvider.Factory
}