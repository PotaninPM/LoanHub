package com.potaninpm.feature_auth.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.utils.dispatchers.DispatcherProvider

object AuthComponentHolder : ComponentHolder<AuthComponent>() {
    override fun build(): AuthComponent {
        val appComponent = AppComponentHolder.get()
        val deps = object : AuthDeps {
            override val authSupabaseApi = appComponent.authSupabaseApi
            override val profileSupabaseApi = appComponent.profileSupabaseApi
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
            override val tokenProvider = appComponent.authTokenProvider
            override val sessionManager = appComponent.sessionManager
        }
        return AuthComponentImpl(deps)
    }
}
