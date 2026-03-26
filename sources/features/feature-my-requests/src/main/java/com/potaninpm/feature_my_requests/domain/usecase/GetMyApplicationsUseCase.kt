package com.potaninpm.feature_my_requests.domain.usecase

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_my_requests.domain.repository.LoanApplicationRepository

class GetMyApplicationsUseCase(
    private val repository: LoanApplicationRepository
) {
    suspend operator fun invoke(isAdmin: Boolean = false): Result<List<LoanApplication>> {
        return runCatching {
            if (isAdmin) {
                repository.getAllApplications()
            } else {
                repository.getMyApplications()
            }
        }
    }
}
