package com.potaninpm.feature_edit_profile.domain.usecase

import com.potaninpm.feature_edit_profile.domain.repository.ProfileRepository
import com.potaninpm.feature_edit_profile.utils.UserProfile

class UpdateProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile): Result<Unit> = repository.updateProfile(profile)
}
