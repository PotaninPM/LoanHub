package com.potaninpm.database.data.repository

import com.potaninpm.database.data.room.dao.LoanDao
import com.potaninpm.database.data.room.entity.LoanEntity
import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.database.models.LoanApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class LoanDraftsRepositoryImpl(
    private val loanDao: LoanDao
) : LoanDraftsRepository {

    override val drafts: Flow<List<LoanApplication>> = loanDao.getAll().map { entities ->
        entities.map { entity ->
            LoanApplication(
                id = entity.id,
                type = entity.type,
                date = entity.date,
                status = entity.status,
                fields = try {
                    Json.decodeFromString(entity.fieldsJson)
                } catch (e: Exception) {
                    emptyList()
                },
                incomeCurrency = entity.incomeCurrency,
                amountCurrency = entity.amountCurrency
            )
        }
    }

    override suspend fun addDraft(application: LoanApplication) {
        loanDao.insert(
            LoanEntity(
                id = application.id,
                type = application.type,
                date = application.date,
                fieldsJson = Json.encodeToString(application.fields),
                incomeCurrency = application.incomeCurrency,
                amountCurrency = application.amountCurrency,
                status = application.status
            )
        )
    }

    override suspend fun deleteDraft(id: String) {
        loanDao.delete(id)
    }

    override suspend fun getDraft(id: String): LoanApplication? {
        val entity = loanDao.getById(id) ?: return null
        return LoanApplication(
            id = entity.id,
            type = entity.type,
            date = entity.date,
            status = entity.status,
            fields = try {
                Json.decodeFromString(entity.fieldsJson)
            } catch (_: Exception) {
                emptyList()
            },
            incomeCurrency = entity.incomeCurrency,
            amountCurrency = entity.amountCurrency
        )
    }
}
