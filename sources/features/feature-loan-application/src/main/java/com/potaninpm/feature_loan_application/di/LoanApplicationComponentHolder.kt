package com.potaninpm.feature_loan_application.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.feature_loan_application.di.component.LoanApplicationComponent
import com.potaninpm.feature_loan_application.di.component.LoanApplicationComponentImpl
import com.potaninpm.utils.dispatchers.DispatcherProvider

object LoanApplicationComponentHolder : ComponentHolder<LoanApplicationComponent>() {
    override fun build(): LoanApplicationComponent {
        val appComponent = AppComponentHolder.get()
        val deps = object : LoanApplicationDeps {
            override val loanDraftsRepository = appComponent.loanDraftsRepository
            override val dadataApi = appComponent.dadataApi
            override val profileSupabaseApi = appComponent.profileSupabaseApi
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
        }
        return LoanApplicationComponentImpl(deps)
    }
}
