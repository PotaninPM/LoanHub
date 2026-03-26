package com.potaninpm.feature_application_details.data.repository

import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_application_details.data.mapper.toDomain
import com.potaninpm.feature_application_details.domain.repository.LoanDetailsRepository
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi

class LoanDetailsRepositoryImpl(
    private val loanDraftsRepository: LoanDraftsRepository,
    private val api: ApplicationsSupabaseApi
) : LoanDetailsRepository {

    override suspend fun getApplication(id: String): Result<LoanApplication?> = runCatching {
        val results = api.getApplicationById("eq.$id")
        results.firstOrNull()?.toDomain()
    }

    override suspend fun getApplicationsByType(type: String): Result<List<LoanApplication>> = runCatching {
        api.getApplications()
            .filter { it.type == type }
            .map { it.toDomain() }
    }

    override suspend fun updateApplicationStatus(id: String, status: String): Result<Unit> = runCatching {
        val results = api.getApplicationById("eq.$id")
        val existing = results.firstOrNull() ?: throw IllegalStateException("Application not found")
        val updated = existing.copy(status = status)
        api.updateApplication("eq.$id", updated)
    }

    override suspend fun deleteApplication(id: String): Result<Unit> = runCatching {
        api.deleteApplication("eq.$id")
        loanDraftsRepository.deleteDraft(id)
    }
}
