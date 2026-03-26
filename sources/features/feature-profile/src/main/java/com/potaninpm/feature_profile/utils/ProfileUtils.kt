package com.potaninpm.feature_profile.utils

fun formatFio(profile: Profile, full: Boolean): String {
    val parts = if (full) {
        listOf(profile.lastName, profile.firstName, profile.patronymic)
    } else {
        listOf(profile.lastName, profile.firstName)
    }.filter { it.isNotBlank() }

    return if (parts.isEmpty()) "" else parts.joinToString(" ")
}
