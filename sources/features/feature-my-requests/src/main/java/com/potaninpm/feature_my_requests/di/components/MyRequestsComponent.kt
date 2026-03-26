package com.potaninpm.feature_my_requests.di.components

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent

interface MyRequestsComponent : DIComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}