package com.potaninpm.feature_my_requests.di

import com.potaninpm.core.auth.SessionManager
import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface MyRequestsDeps {
    val loanDraftsRepository: LoanDraftsRepository
    val applicationsSupabaseApi: ApplicationsSupabaseApi
    val sessionManager: SessionManager
    val dispatcherProvider: DispatcherProvider
}
