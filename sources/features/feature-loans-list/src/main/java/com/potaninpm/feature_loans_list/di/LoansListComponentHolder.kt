package com.potaninpm.feature_loans_list.di

import com.potaninpm.common.api.ComponentHolder
import com.potaninpm.common.impl.AppComponentHolder
import com.potaninpm.feature_loans_list.di.component.LoansListComponent
import com.potaninpm.feature_loans_list.di.component.LoansListComponentImpl
import com.potaninpm.utils.dispatchers.DispatcherProvider

object LoansListComponentHolder : ComponentHolder<LoansListComponent>() {
    override fun build(): LoansListComponent {
        val appComponent = AppComponentHolder.get()

        val deps = object : LoansListDeps {
            override val loanDraftsRepository = appComponent.loanDraftsRepository
            override val loansSupabaseApi = appComponent.loansSupabaseApi
            override val dispatcherProvider: DispatcherProvider = appComponent.dispatcherProvider
        }
        return LoansListComponentImpl(deps)
    }
}
