package com.potaninpm.feature_profile.di

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface ProfileDeps {
    val profileSupabaseApi: ProfileSupabaseApi
    val sessionManager: SessionManager
    val dispatcherProvider: DispatcherProvider
}
