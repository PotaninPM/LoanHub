package com.potaninpm.feature_my_requests.data.repository

import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_my_requests.data.mapper.toDomain
import com.potaninpm.feature_my_requests.domain.repository.LoanApplicationRepository
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi
import kotlinx.coroutines.flow.first

class LoanApplicationRepositoryImpl(
    private val loanDraftsRepository: LoanDraftsRepository,
    private val api: ApplicationsSupabaseApi
) : LoanApplicationRepository {

    override suspend fun deleteApplication(id: String) {
        loanDraftsRepository.deleteDraft(id)
    }

    override suspend fun getMyApplications(): List<LoanApplication> {
        val remoteApps = try {
            api.getApplications(status = "neq.DRAFT").map { it.toDomain() }.reversed()
        } catch (_: Exception) {
            emptyList()
        }

        val localDrafts = getDrafts()

        return localDrafts + remoteApps
    }

    override suspend fun getAllApplications(): List<LoanApplication> {
        return try {
            api.getApplications(status = null).map { it.toDomain() }.reversed()
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getDrafts(): List<LoanApplication> {
        return loanDraftsRepository.drafts.first()
    }

    override suspend fun updateApplicationStatus(id: String, status: String) {
        val results = api.getApplicationById("eq.$id")
        val existing = results.firstOrNull() ?: throw IllegalStateException("Application not found")
        val updated = existing.copy(status = status)
        api.updateApplication("eq.$id", updated)
    }
}
