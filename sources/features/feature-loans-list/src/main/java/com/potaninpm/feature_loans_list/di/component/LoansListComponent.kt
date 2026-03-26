package com.potaninpm.feature_loans_list.di.component

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent

interface LoansListComponent : DIComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}