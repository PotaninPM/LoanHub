package com.potaninpm.feature_edit_profile.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.feature_edit_profile.di.component.EditProfileComponent
import com.potaninpm.feature_edit_profile.di.component.EditProfileComponentImpl
import com.potaninpm.utils.dispatchers.DispatcherProvider

object EditProfileComponentHolder : ComponentHolder<EditProfileComponent>() {
    override fun build(): EditProfileComponent {
        val appComponent = AppComponentHolder.get()
        val deps = object : EditProfileDeps {
            override val profileSupabaseApi = appComponent.profileSupabaseApi
            override val sessionManager = appComponent.sessionManager
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
        }
        return EditProfileComponentImpl(deps)
    }
}
