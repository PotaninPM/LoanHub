package com.potaninpm.feature_edit_profile.di.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_edit_profile.data.repository.ProfileRepositoryImpl
import com.potaninpm.feature_edit_profile.di.EditProfileDeps
import com.potaninpm.feature_edit_profile.domain.usecase.GetProfileUseCase
import com.potaninpm.feature_edit_profile.domain.usecase.UpdateProfileUseCase
import com.potaninpm.feature_edit_profile.presentation.viewmodel.EditProfileViewModel

class EditProfileComponentImpl(
    private val deps: EditProfileDeps
) : EditProfileComponent {

    private val repository by lazy {
        ProfileRepositoryImpl(deps.profileSupabaseApi, deps.sessionManager)
    }

    private val getProfileUseCase by lazy {
        GetProfileUseCase(repository)
    }

    private val updateProfileUseCase by lazy {
        UpdateProfileUseCase(repository)
    }

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EditProfileViewModel(
                    getProfileUseCase,
                    updateProfileUseCase,
                    deps.dispatcherProvider
                ) as T
            }
        }
    }
}