package com.potaninpm.feature_application_details.di.component

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent

interface ApplicationDetailsComponent : DIComponent {
    fun provideViewModelFactory(applicationId: String): ViewModelProvider.Factory
}
