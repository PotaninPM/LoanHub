package com.potaninpm.feature_application_details.di.component

import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_application_details.data.repository.LoanDetailsRepositoryImpl
import com.potaninpm.feature_application_details.di.ApplicationDetailsDeps
import com.potaninpm.feature_application_details.presentation.viewmodel.DetailsViewModelFactory

class ApplicationDetailsComponentImpl(
    private val deps: ApplicationDetailsDeps
) : ApplicationDetailsComponent {

    private val repository by lazy {
        LoanDetailsRepositoryImpl(deps.loanDraftsRepository, deps.applicationsSupabaseApi)
    }

    private val isAdmin: Boolean
        get() = deps.sessionManager.userRole == ADMIN_ROLE

    override fun provideViewModelFactory(applicationId: String): ViewModelProvider.Factory {
        return DetailsViewModelFactory(
            applicationId,
            repository,
            deps.currencyRepository,
            isAdmin,
            deps.dispatcherProvider
        )
    }

    private companion object {
        const val ADMIN_ROLE = "admin"
    }
}