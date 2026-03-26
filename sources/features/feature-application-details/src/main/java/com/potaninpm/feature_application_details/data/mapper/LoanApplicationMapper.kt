package com.potaninpm.feature_application_details.data.mapper

import com.potaninpm.database.models.FormField
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.network.supabase.applications.models.LoanApplicationDto
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

internal fun LoanApplicationDto.toDomain(): LoanApplication {
    return LoanApplication(
        id = id ?: throw IllegalStateException("ID is missing in DTO"),
        type = type,
        date = date,
        status = status,
        incomeCurrency = incomeCurrency,
        amountCurrency = amountCurrency,
        fields = json.decodeFromString<List<FormField>>(fields)
    )
}
