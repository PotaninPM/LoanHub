package com.potaninpm.feature_loan_application.data.mapper

import com.potaninpm.database.models.FormField
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.network.supabase.applications.models.LoanApplicationDto
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

internal fun LoanApplication.toDto(): LoanApplicationDto {
    return LoanApplicationDto(
        id = id,
        type = type,
        date = date,
        incomeCurrency = incomeCurrency,
        amountCurrency = amountCurrency,
        fields = json.encodeToString(fields)
    )
}

internal fun LoanApplicationDto.toDomain(): LoanApplication {
    return LoanApplication(
        id = id ?: throw IllegalStateException("ID is missing in DTO"),
        type = type,
        date = date,
        incomeCurrency = incomeCurrency,
        amountCurrency = amountCurrency,
        fields = json.decodeFromString<List<FormField>>(fields)
    )
}
