package com.potaninpm.feature_edit_profile.domain.repository

import com.potaninpm.feature_edit_profile.utils.UserProfile

interface ProfileRepository {
    suspend fun getProfile(): Result<UserProfile>
    suspend fun updateProfile(profile: UserProfile): Result<Unit>
}
