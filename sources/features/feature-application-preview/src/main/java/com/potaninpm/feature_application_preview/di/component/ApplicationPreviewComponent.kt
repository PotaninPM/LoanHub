package com.potaninpm.feature_application_preview.di.component

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent
import com.potaninpm.database.models.FormField

interface ApplicationPreviewComponent : DIComponent {
    fun provideViewModelFactory(
        initialFields: List<FormField>,
        incomeCurrency: String,
        amountCurrency: String,
        loanType: String
    ): ViewModelProvider.Factory
}
