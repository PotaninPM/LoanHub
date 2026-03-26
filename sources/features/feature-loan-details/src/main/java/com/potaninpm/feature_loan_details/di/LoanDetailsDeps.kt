package com.potaninpm.feature_loan_details.di

import com.potaninpm.network.supabase.loans.LoansSupabaseApi
import com.potaninpm.utils.dispatchers.DispatcherProvider

interface LoanDetailsDeps {
    val loansSupabaseApi: LoansSupabaseApi
    val dispatcherProvider: DispatcherProvider
}