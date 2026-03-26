package com.potaninpm.feature_auth.domain.usecase

import com.potaninpm.feature_auth.domain.repository.AuthRepository
import com.potaninpm.network.supabase.auth.models.AuthRequest

class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(request: AuthRequest): Result<Unit> {
        return repository.login(request)
    }
}
