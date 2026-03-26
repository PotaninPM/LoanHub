package com.potaninpm.feature_edit_profile.domain.usecase

import com.potaninpm.feature_edit_profile.domain.repository.ProfileRepository
import com.potaninpm.feature_edit_profile.utils.UserProfile

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<UserProfile> = repository.getProfile()
}
