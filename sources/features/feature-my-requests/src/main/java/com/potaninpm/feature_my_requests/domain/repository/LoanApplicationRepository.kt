package com.potaninpm.feature_my_requests.domain.repository

import com.potaninpm.database.models.LoanApplication

interface LoanApplicationRepository {
    suspend fun deleteApplication(id: String)
    suspend fun getMyApplications(): List<LoanApplication>
    suspend fun getAllApplications(): List<LoanApplication>
    suspend fun getDrafts(): List<LoanApplication>
    suspend fun updateApplicationStatus(id: String, status: String)
}
