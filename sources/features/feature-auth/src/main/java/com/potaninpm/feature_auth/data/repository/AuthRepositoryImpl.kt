package com.potaninpm.feature_auth.data.repository

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.feature_auth.domain.repository.AuthRepository
import com.potaninpm.network.supabase.TokenProvider
import com.potaninpm.network.supabase.auth.AuthSupabaseApi
import com.potaninpm.network.supabase.auth.models.AuthRequest
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val api: AuthSupabaseApi,
    private val profileApi: ProfileSupabaseApi,
    private val sessionManager: SessionManager,
    private val tokenProvider: TokenProvider
) : AuthRepository {

    override suspend fun login(request: AuthRequest): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val response = api.signIn(request = request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    tokenProvider.saveTokens(
                        accessToken = body.accessToken,
                        refreshToken = body.refreshToken,
                        expiresIn = body.expiresIn
                    )
                    
                    runCatching {
                        profileApi.getProfile().firstOrNull()?.let { profile ->
                            sessionManager.userRole = profile.role
                        }
                    }
                    Unit
                } else {
                    throw RuntimeException("Empty response body")
                }
            } else {
                throw RuntimeException("Login failde: ${response.code()} ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun register(request: AuthRequest): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val response = api.signUp(request = request)
            if (response.isSuccessful) {
                val json = response.body()?.asJsonObject
                if (json != null) {
                    if (json.has("access_token")) {
                        val accessToken = json.get("access_token").asString
                        val refreshToken = json.get("refresh_token").asString
                        val expiresIn = json.get("expires_in").asInt
                        if (accessToken.isNotEmpty()) {
                            tokenProvider.saveTokens(
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                                expiresIn = expiresIn
                            )
                        }
                    } else {
                        throw RuntimeException("Unknown format")
                    }
                } else {
                    throw RuntimeException("Empty body")
                }
            } else {
                throw RuntimeException("Registration failed: ${response.code()} ${response.errorBody()?.string()}")
            }
        }
    }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        tokenProvider.clear()
    }
}
