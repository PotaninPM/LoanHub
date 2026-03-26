package com.potaninpm.feature_application_details.domain.usecase

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_application_details.domain.repository.LoanDetailsRepository

class GetLoanDetailsUseCase(
    private val repository: LoanDetailsRepository
) {
    suspend operator fun invoke(id: String): Result<LoanApplication?> {
        return repository.getApplication(id)
    }
}
