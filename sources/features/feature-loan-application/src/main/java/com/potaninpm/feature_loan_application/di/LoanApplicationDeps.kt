package com.potaninpm.feature_loan_application.di

import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.network.dadata.DadataApi
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface LoanApplicationDeps {
    val loanDraftsRepository: LoanDraftsRepository
    val dadataApi: DadataApi
    val profileSupabaseApi: ProfileSupabaseApi
    val dispatcherProvider: DispatcherProvider
}
