package com.potaninpm.core.auth

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager(
    private val prefs: SharedPreferences
) {

    var accessToken: String?
        get() = prefs.getString(ACCESS_TOKEN, null)
        set(value) = prefs.edit { putString(ACCESS_TOKEN, value) }

    var refreshToken: String?
        get() = prefs.getString(REFRESH_TOKEN, null)
        set(value) = prefs.edit { putString(REFRESH_TOKEN, value) }

    private val _userRoleFlow = MutableStateFlow<String?>(userRole)
    val userRoleFlow: StateFlow<String?> = _userRoleFlow.asStateFlow()

    var userRole: String?
        get() = prefs.getString(USER_ROLE, null)
        set(value) {
            prefs.edit { putString(USER_ROLE, value) }
            _userRoleFlow.value = value
        }

    fun saveSession(
        access: String,
        refresh: String
    ) {
        accessToken = access
        refreshToken = refresh
    }

    fun clear() {
        prefs.edit { clear() }
        _userRoleFlow.value = null
    }

    fun isAuthenticated(): Boolean = !accessToken.isNullOrEmpty()

    private companion object {
        const val REFRESH_TOKEN = "refresh_token"
        const val ACCESS_TOKEN = "access_token"
        const val USER_ROLE = "user_role"
    }
}
