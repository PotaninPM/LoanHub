package com.potaninpm.feature_loan_application.di.component

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent

interface LoanApplicationComponent : DIComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}