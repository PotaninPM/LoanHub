package com.potaninpm.feature_application_preview.data.repository

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_application_preview.domain.repository.SubmitApplicationRepository
import com.potaninpm.network.supabase.applications.ApplicationsSupabaseApi
import com.potaninpm.network.supabase.applications.models.LoanApplicationCreateDto
import kotlinx.serialization.json.Json

class SubmitApplicationRepositoryImpl(
    private val api: ApplicationsSupabaseApi
) : SubmitApplicationRepository {

    override suspend fun submitApplication(application: LoanApplication): Result<Unit> = runCatching {
        if (application.id.isEmpty()) {
            api.createApplication(application.toCreateDto())
        }
    }

    private fun LoanApplication.toCreateDto(): LoanApplicationCreateDto {
        return LoanApplicationCreateDto(
            type = this.type,
            date = this.date,
            status = "SUBMITTED",
            incomeCurrency = this.incomeCurrency,
            amountCurrency = this.amountCurrency,
            fields = Json.encodeToString(this.fields)
        )
    }
}
