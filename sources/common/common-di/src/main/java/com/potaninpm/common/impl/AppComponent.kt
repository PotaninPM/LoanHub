package com.potaninpm.common.impl

import com.potaninpm.common.api.DIComponent
import com.potaninpm.core.auth.SessionManager
import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.network.dadata.DadataApi
import com.potaninpm.network.rates.UnirateApi
import com.potaninpm.network.repository.CurrencyRepository
import com.potaninpm.network.supabase.TokenProvider
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi
import com.potaninpm.network.supabase.auth.AuthSupabaseApi
import com.potaninpm.network.supabase.loans.LoansSupabaseApi
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface AppComponent : DIComponent {
    val currencyRepository: CurrencyRepository
    val applicationsSupabaseApi: ApplicationsSupabaseApi
    val unirateApi: UnirateApi
    val loansSupabaseApi: LoansSupabaseApi
    val authSupabaseApi: AuthSupabaseApi
    val profileSupabaseApi: ProfileSupabaseApi
    val dadataApi: DadataApi
    val authTokenProvider: TokenProvider
    val loanDraftsRepository: LoanDraftsRepository
    val dispatcherProvider: DispatcherProvider
    val sessionManager: SessionManager
}
