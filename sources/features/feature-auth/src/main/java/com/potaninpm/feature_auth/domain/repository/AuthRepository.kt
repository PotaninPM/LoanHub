package com.potaninpm.feature_auth.domain.repository



import com.potaninpm.network.supabase.auth.models.AuthRequest

interface AuthRepository {
    suspend fun login(request: AuthRequest): Result<Unit>
    suspend fun register(request: AuthRequest): Result<Unit>
    suspend fun logout()
}
