package com.potaninpm.feature_application_preview.domain.usecase

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_application_preview.domain.repository.SubmitApplicationRepository

class SubmitLoanUseCase(
    private val repository: SubmitApplicationRepository
) {
    suspend operator fun invoke(application: LoanApplication): Result<Unit> {
        return repository.submitApplication(application)
    }
}
