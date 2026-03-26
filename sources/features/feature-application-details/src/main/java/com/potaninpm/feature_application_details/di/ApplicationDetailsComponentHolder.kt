package com.potaninpm.feature_application_details.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.feature_application_details.di.component.ApplicationDetailsComponent
import com.potaninpm.feature_application_details.di.component.ApplicationDetailsComponentImpl
import com.potaninpm.utils.dispatchers.DispatcherProvider

object ApplicationDetailsComponentHolder : ComponentHolder<ApplicationDetailsComponent>() {
    override fun build(): ApplicationDetailsComponent {
        val appComponent = AppComponentHolder.get()

        val deps = object : ApplicationDetailsDeps {
            override val loanDraftsRepository = appComponent.loanDraftsRepository
            override val applicationsSupabaseApi = appComponent.applicationsSupabaseApi
            override val currencyRepository = appComponent.currencyRepository
            override val sessionManager = appComponent.sessionManager
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
        }
        return ApplicationDetailsComponentImpl(deps)
    }
}
