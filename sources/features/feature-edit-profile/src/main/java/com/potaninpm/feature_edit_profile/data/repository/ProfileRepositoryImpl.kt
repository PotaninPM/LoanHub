package com.potaninpm.feature_edit_profile.data.repository

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.feature_edit_profile.domain.repository.ProfileRepository
import com.potaninpm.feature_edit_profile.utils.UserProfile
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import toDomain
import toDto

class ProfileRepositoryImpl(
    private val api: ProfileSupabaseApi,
    private val sessionManager: SessionManager
) : ProfileRepository {

    override suspend fun getProfile(): Result<UserProfile> = runCatching {
        val profiles = api.getProfile()
        val profileDto = profiles.firstOrNull()
        
        if (profileDto != null) {
            sessionManager.userRole = profileDto.role
        }

        profileDto?.toDomain() ?: UserProfile()
    }

    override suspend fun updateProfile(profile: UserProfile): Result<Unit> = runCatching {
        val idFilter = "eq.${profile.id}"
        api.updateProfile(idFilter, profile.toDto())
    }
}
