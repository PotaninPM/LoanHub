package com.potaninpm.feature_my_requests.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.potaninpm.feature_my_requests.di.components.MyRequestsComponent
import com.potaninpm.feature_my_requests.presentation.viewmodel.MyRequestsViewModel

class MyRequestsComponentStub(
    private val viewModel: MyRequestsViewModel
) : MyRequestsComponent {

    override fun provideViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModel as T
            }
        }
    }
}
