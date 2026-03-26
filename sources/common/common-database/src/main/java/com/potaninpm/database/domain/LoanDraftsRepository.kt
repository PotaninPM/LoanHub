package com.potaninpm.database.domain

import com.potaninpm.database.models.LoanApplication
import kotlinx.coroutines.flow.Flow

interface LoanDraftsRepository {
    val drafts: Flow<List<LoanApplication>>
    suspend fun addDraft(application: LoanApplication)
    suspend fun deleteDraft(id: String)
    suspend fun getDraft(id: String): LoanApplication?
}
