package com.potaninpm.feature_auth.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_auth.data.repository.AuthRepositoryImpl
import com.potaninpm.feature_auth.domain.repository.AuthRepository
import com.potaninpm.feature_auth.domain.usecase.LoginUseCase
import com.potaninpm.feature_auth.domain.usecase.RegisterUseCase
import com.potaninpm.feature_auth.presentation.viewModels.AuthViewModel

class AuthComponentImpl(
    private val deps: AuthDeps
) : AuthComponent {

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(
            api = deps.authSupabaseApi,
            profileApi = deps.profileSupabaseApi,
            sessionManager = deps.sessionManager,
            tokenProvider = deps.tokenProvider
        )
    }

    val loginUseCase by lazy { LoginUseCase(authRepository) }
    val registerUseCase by lazy { RegisterUseCase(authRepository) }

    override fun provideViewModelFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when (modelClass) {
                AuthViewModel::class.java -> AuthViewModel(
                    loginUseCase = loginUseCase,
                    registerUseCase = registerUseCase,
                    dispatcherProvider = deps.dispatcherProvider
                ) as T
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}