package com.potaninpm.feature_edit_profile.presentation.state

import com.potaninpm.feature_edit_profile.utils.UserProfile

data class EditProfileState(
    val profile: UserProfile = UserProfile(),
    val fioInput: String = "",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val error: String? = null,
    val saveSuccess: Boolean = false
)