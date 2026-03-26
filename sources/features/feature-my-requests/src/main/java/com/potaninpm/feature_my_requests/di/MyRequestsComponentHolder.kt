package com.potaninpm.feature_my_requests.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.feature_my_requests.di.components.MyRequestsComponent
import com.potaninpm.feature_my_requests.di.components.MyRequestsComponentImpl
import com.potaninpm.utils.dispatchers.DispatcherProvider

object MyRequestsComponentHolder : ComponentHolder<MyRequestsComponent>() {
    override fun build(): MyRequestsComponent {
        val appComponent = AppComponentHolder.get()

        val deps = object : MyRequestsDeps {
            override val loanDraftsRepository = appComponent.loanDraftsRepository
            override val applicationsSupabaseApi = appComponent.applicationsSupabaseApi
            override val sessionManager = appComponent.sessionManager
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
        }
        return MyRequestsComponentImpl(deps)
    }
}
