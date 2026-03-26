package com.potaninpm.feature_profile.data.repository

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.feature_profile.data.mapper.toDomain
import com.potaninpm.feature_profile.domain.repository.ProfileRepository
import com.potaninpm.feature_profile.utils.Profile
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi

class ProfileRepositoryImpl(
    private val api: ProfileSupabaseApi,
    private val sessionManager: SessionManager
) : ProfileRepository {

    override suspend fun getProfile(): Result<Profile> = runCatching {
        val profiles = api.getProfile()
        val profileDto = profiles.firstOrNull()

        if (profileDto != null) {
            sessionManager.userRole = profileDto.role
        }

        profileDto?.toDomain() ?: Profile()
    }

    override suspend fun logout() {
        sessionManager.clear()
    }
}
