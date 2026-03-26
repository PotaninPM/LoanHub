package com.potaninpm.feature_profile.domain.repository

import com.potaninpm.feature_profile.utils.Profile

interface ProfileRepository {
    suspend fun getProfile(): Result<Profile>
    suspend fun logout()
}
