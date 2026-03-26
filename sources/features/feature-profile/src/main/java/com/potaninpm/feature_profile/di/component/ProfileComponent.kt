package com.potaninpm.feature_profile.di.component

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent

interface ProfileComponent : DIComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
