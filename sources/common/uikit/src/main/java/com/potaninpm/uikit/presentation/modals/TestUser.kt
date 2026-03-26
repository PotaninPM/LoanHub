package com.potaninpm.uikit.presentation.modals

data class TestUser(
    val email: String,
    val password: String,
    val badgeStatus: String? = null,
    val badgeColorStart: Long? = null,
    val badgeColorEnd: Long? = null
)