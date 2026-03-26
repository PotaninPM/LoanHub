package com.potaninpm.feature_loan_details.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.utils.dispatchers.DispatcherProvider

object LoanDetailsComponentHolder : ComponentHolder<LoanDetailsComponent>() {
    override fun build(): LoanDetailsComponent {
        val appComponent = AppComponentHolder.get()
        val deps = object : LoanDetailsDeps {
            override val loansSupabaseApi = appComponent.loansSupabaseApi
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
        }
        return LoanDetailsComponentImpl(deps)
    }
}
