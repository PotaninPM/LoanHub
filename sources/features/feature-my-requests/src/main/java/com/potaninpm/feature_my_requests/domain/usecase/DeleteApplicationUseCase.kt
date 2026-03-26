package com.potaninpm.feature_my_requests.domain.usecase

import com.potaninpm.feature_my_requests.domain.repository.LoanApplicationRepository

class DeleteApplicationUseCase(
    private val repository: LoanApplicationRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteApplication(id)
    }
}
