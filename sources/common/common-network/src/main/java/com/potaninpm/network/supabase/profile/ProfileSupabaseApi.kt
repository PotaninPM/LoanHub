package com.potaninpm.network.supabase.profile

import com.potaninpm.network.supabase.profile.models.UserProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface ProfileSupabaseApi {

    @GET("/rest/v1/profiles")
    suspend fun getProfile(
        @Query("select") select: String = "*"
    ): List<UserProfileDto>

    @PATCH("/rest/v1/profiles")
    suspend fun updateProfile(
        @Query("id") id: String,
        @Body body: UserProfileDto
    )
}