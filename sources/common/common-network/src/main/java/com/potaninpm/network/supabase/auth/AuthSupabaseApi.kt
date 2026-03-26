package com.potaninpm.network.supabase.auth

import com.google.gson.JsonElement
import com.potaninpm.network.supabase.auth.models.AuthRequest
import com.potaninpm.network.supabase.auth.models.AuthResponse
import com.potaninpm.network.supabase.auth.models.RefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthSupabaseApi {
    @POST("auth/v1/signup")
    suspend fun signUp(
        @Body request: AuthRequest
    ): Response<JsonElement>

    @POST("auth/v1/token")
    suspend fun signIn(
        @Query("grant_type") grantType: String = "password",
        @Body request: AuthRequest
    ): Response<AuthResponse>

    @POST("auth/v1/token")
    suspend fun refreshToken(
        @Query("grant_type") grantType: String = "refresh_token",
        @Body request: RefreshRequest
    ): Response<AuthResponse>
}
