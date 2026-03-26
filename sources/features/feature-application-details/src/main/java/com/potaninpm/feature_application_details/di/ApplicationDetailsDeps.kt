package com.potaninpm.feature_application_details.di

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.network.repository.CurrencyRepository
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface ApplicationDetailsDeps {
    val loanDraftsRepository: LoanDraftsRepository
    val applicationsSupabaseApi: ApplicationsSupabaseApi
    val currencyRepository: CurrencyRepository
    val sessionManager: SessionManager
    val dispatcherProvider: DispatcherProvider
}
