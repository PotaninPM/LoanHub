package com.potaninpm.feature_profile.di.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_profile.data.repository.ProfileRepositoryImpl
import com.potaninpm.feature_profile.di.ProfileDeps
import com.potaninpm.feature_profile.presentation.viewmodel.ProfileViewModel

class ProfileComponentImpl(
    private val deps: ProfileDeps
) : ProfileComponent {

    private val repository by lazy {
        ProfileRepositoryImpl(deps.profileSupabaseApi, deps.sessionManager)
    }

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(repository, deps.dispatcherProvider) as T
            }
        }
    }
}