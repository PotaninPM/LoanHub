package com.potaninpm.feature_loans_list.di

import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.network.supabase.loans.LoansSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface LoansListDeps {
    val loanDraftsRepository: LoanDraftsRepository
    val loansSupabaseApi: LoansSupabaseApi
    val dispatcherProvider: DispatcherProvider
}
