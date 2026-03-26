package com.potaninpm.feature_application_preview.domain.repository

import com.potaninpm.database.models.LoanApplication

interface SubmitApplicationRepository {
    suspend fun submitApplication(application: LoanApplication): Result<Unit>
}
