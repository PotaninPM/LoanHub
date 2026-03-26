package com.potaninpm.feature_application_preview.di

import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.network.repository.CurrencyRepository
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface ApplicationPreviewDeps {
    val applicationsSupabaseApi: ApplicationsSupabaseApi
    val dispatcherProvider: DispatcherProvider
    val loanDraftsRepository: LoanDraftsRepository
    val currencyRepository: CurrencyRepository
}
