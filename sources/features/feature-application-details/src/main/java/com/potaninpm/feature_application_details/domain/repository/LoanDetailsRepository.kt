package com.potaninpm.feature_application_details.domain.repository

import com.potaninpm.database.models.LoanApplication

interface LoanDetailsRepository {
    suspend fun getApplication(id: String): Result<LoanApplication?>
    suspend fun getApplicationsByType(type: String): Result<List<LoanApplication>>
    suspend fun updateApplicationStatus(id: String, status: String): Result<Unit>
    suspend fun deleteApplication(id: String): Result<Unit>
}
