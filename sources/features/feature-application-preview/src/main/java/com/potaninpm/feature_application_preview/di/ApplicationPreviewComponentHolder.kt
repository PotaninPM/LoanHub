package com.potaninpm.feature_application_preview.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.feature_application_preview.di.component.ApplicationPreviewComponent
import com.potaninpm.feature_application_preview.di.component.ApplicationPreviewComponentImpl
import com.potaninpm.utils.dispatchers.DispatcherProvider

object ApplicationPreviewComponentHolder : ComponentHolder<ApplicationPreviewComponent>() {
    override fun build(): ApplicationPreviewComponent {
        val appComponent = AppComponentHolder.get()

        val deps = object : ApplicationPreviewDeps {
            override val applicationsSupabaseApi = appComponent.applicationsSupabaseApi
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
            override val loanDraftsRepository: LoanDraftsRepository = appComponent.loanDraftsRepository
            override val currencyRepository = appComponent.currencyRepository
        }
        return ApplicationPreviewComponentImpl(deps)
    }
}
