package com.potaninpm.feature_profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potaninpm.feature_profile.domain.repository.ProfileRepository
import com.potaninpm.feature_profile.presentation.state.ProfileState
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ProfileViewModel(
    private val repository: ProfileRepository,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile(isRefresh: Boolean = false) {
        viewModelScope.launch(dispatcherProvider.io) {
            if (isRefresh) {
                _state.update { it.copy(isRefreshing = true, error = null) }
            } else {
                _state.update { it.copy(isLoading = true, error = null) }
            }
            
            repository.getProfile().fold(
                onSuccess = { profile ->
                    _state.update { it.copy(profile = profile, isLoading = false, isRefreshing = false) }
                },
                onFailure = { e ->
                    _state.update { it.copy(isLoading = false, isRefreshing = false, error = e.message) }
                }
            )
        }
    }

    fun refresh() {
        loadProfile(isRefresh = true)
    }

    fun logout() {
        viewModelScope.launch(dispatcherProvider.io) {
            repository.logout()
        }
    }
}
