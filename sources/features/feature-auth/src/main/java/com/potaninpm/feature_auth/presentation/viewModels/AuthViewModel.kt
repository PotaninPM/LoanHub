package com.potaninpm.feature_auth.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potaninpm.feature_auth.domain.usecase.LoginUseCase
import com.potaninpm.feature_auth.domain.usecase.RegisterUseCase
import com.potaninpm.feature_auth.presentation.state.AuthState
import com.potaninpm.network.supabase.auth.models.AuthRequest
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthorized)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()



    fun login(email: String, pass: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _isLoading.update { true }
            _error.update { null }
            
            loginUseCase(AuthRequest(email, pass)).onSuccess {
                _authState.update { AuthState.Authorized }
            }.onFailure { error ->
                _error.update { error.message }
            }
            
            _isLoading.update { false }
        }
    }

    fun register(email: String, pass: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _isLoading.update { true }
            _error.update { null }
            
            registerUseCase(AuthRequest(email, pass)).onSuccess {
                _authState.update { AuthState.Authorized }
            }.onFailure { error ->
                _error.update { error.message }
            }
            
            _isLoading.update { false }
        }
    }
}
