package com.potaninpm.feature_profile.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.feature_profile.di.component.ProfileComponent
import com.potaninpm.feature_profile.di.component.ProfileComponentImpl
import com.potaninpm.utils.dispatchers.DispatcherProvider

object ProfileComponentHolder : ComponentHolder<ProfileComponent>() {
    override fun build(): ProfileComponent {
        val appComponent = AppComponentHolder.get()
        val deps = object : ProfileDeps {
            override val profileSupabaseApi = appComponent.profileSupabaseApi
            override val sessionManager = appComponent.sessionManager
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
        }
        return ProfileComponentImpl(deps)
    }
}
