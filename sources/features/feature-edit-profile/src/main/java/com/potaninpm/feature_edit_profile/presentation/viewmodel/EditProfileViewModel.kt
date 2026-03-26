package com.potaninpm.feature_edit_profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potaninpm.feature_edit_profile.domain.usecase.GetProfileUseCase
import com.potaninpm.feature_edit_profile.domain.usecase.UpdateProfileUseCase
import com.potaninpm.feature_edit_profile.presentation.state.EditProfileState
import com.potaninpm.feature_edit_profile.utils.UserProfile
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state: StateFlow<EditProfileState> = _state.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch(dispatcherProvider.io) {
            _state.update { it.copy(isLoading = true, error = null) }
            getProfileUseCase().fold(
                onSuccess = { profile ->
                    val fio = listOf(profile.lastName, profile.firstName, profile.patronymic)
                        .filter { it.isNotBlank() }
                        .joinToString(" ")
                    _state.update { it.copy(profile = profile, fioInput = fio, isLoading = false) }
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            )
        }
    }

    fun updateFio(input: String) {
        val parts = input.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }
        val lastName = parts.getOrNull(0).orEmpty()
        val firstName = parts.getOrNull(1).orEmpty()
        val patronymic = parts.drop(2).joinToString(" ")

        _state.update { 
            it.copy(
                fioInput = input,
                profile = it.profile.copy(
                    lastName = lastName,
                    firstName = firstName,
                    patronymic = patronymic
                )
            )
        }
    }

    fun updateField(updater: UserProfile.() -> UserProfile) {
        _state.update { it.copy(profile = it.profile.updater()) }
    }

    fun saveProfile() {
        viewModelScope.launch(dispatcherProvider.io) {
            _state.update { it.copy(isSaving = true, error = null) }
            updateProfileUseCase(_state.value.profile).fold(
                onSuccess = {
                    _state.update { it.copy(isSaving = false, saveSuccess = true) }
                },
                onFailure = { e ->
                    _state.update { it.copy(isSaving = false, error = e.message) }
                }
            )
        }
    }
}
