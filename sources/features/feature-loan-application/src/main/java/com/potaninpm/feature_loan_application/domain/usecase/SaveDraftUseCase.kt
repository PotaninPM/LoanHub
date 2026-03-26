package com.potaninpm.feature_loan_application.domain.usecase

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_loan_application.domain.repository.LoanSubmissionRepository

class SaveDraftUseCase(
    private val repository: LoanSubmissionRepository
) {
    suspend operator fun invoke(application: LoanApplication): Result<String> {
        return repository.saveDraft(application)
    }
}
