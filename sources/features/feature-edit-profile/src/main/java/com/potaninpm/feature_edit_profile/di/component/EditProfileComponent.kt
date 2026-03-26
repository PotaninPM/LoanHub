package com.potaninpm.feature_edit_profile.di.component

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.common.api.DIComponent

interface EditProfileComponent : DIComponent {
    fun provideViewModelFactory(): ViewModelProvider.Factory
}
