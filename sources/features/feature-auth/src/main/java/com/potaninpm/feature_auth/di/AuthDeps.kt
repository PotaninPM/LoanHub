package com.potaninpm.feature_auth.di

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.network.supabase.TokenProvider
import com.potaninpm.network.supabase.auth.AuthSupabaseApi
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface AuthDeps {
    val authSupabaseApi: AuthSupabaseApi
    val profileSupabaseApi: ProfileSupabaseApi
    val dispatcherProvider: DispatcherProvider
    val tokenProvider: TokenProvider
    val sessionManager: SessionManager
}
