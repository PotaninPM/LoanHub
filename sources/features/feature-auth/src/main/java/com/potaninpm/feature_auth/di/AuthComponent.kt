package com.potaninpm.feature_auth.di

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent

interface AuthComponent : DIComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
