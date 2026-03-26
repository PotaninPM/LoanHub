package com.potaninpm.feature_loan_application.domain.repository

import com.potaninpm.database.models.LoanApplication

interface LoanSubmissionRepository {
    suspend fun saveDraft(application: LoanApplication): Result<String>
}
