package com.potaninpm.feature_profile.presentation.state

import com.potaninpm.feature_profile.utils.Profile


data class ProfileState(
    val profile: Profile = Profile(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)