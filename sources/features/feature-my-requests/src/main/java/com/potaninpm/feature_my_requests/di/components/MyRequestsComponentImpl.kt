package com.potaninpm.feature_my_requests.di.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_my_requests.data.repository.LoanApplicationRepositoryImpl
import com.potaninpm.feature_my_requests.di.MyRequestsDeps
import com.potaninpm.feature_my_requests.domain.usecase.DeleteApplicationUseCase
import com.potaninpm.feature_my_requests.domain.usecase.GetMyApplicationsUseCase
import com.potaninpm.feature_my_requests.presentation.viewmodel.MyRequestsViewModel
import kotlinx.coroutines.flow.map

class MyRequestsComponentImpl(
    private val deps: MyRequestsDeps
) : MyRequestsComponent {

    private val repository by lazy {
        LoanApplicationRepositoryImpl(deps.loanDraftsRepository, deps.applicationsSupabaseApi)
    }

    private val getMyApplicationsUseCase by lazy {
        GetMyApplicationsUseCase(repository)
    }

    private val deleteApplicationUseCase by lazy {
        DeleteApplicationUseCase(repository)
    }

    private val isAdmin: Boolean
        get() = deps.sessionManager.userRole == ADMIN_ROLE

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MyRequestsViewModel(
                    getMyApplicationsUseCase,
                    deleteApplicationUseCase,
                    repository,
                    deps.sessionManager.userRoleFlow.map { it == ADMIN_ROLE },
                    deps.dispatcherProvider
                ) as T
            }
        }
    }

    private companion object {
        const val ADMIN_ROLE = "admin"
    }
}
