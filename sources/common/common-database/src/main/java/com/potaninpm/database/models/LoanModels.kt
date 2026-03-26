package com.potaninpm.database.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class LoanApplication(
    val id: String = UUID.randomUUID().toString(),
    val type: String,
    val date: String,
    val status: String = "DRAFT",
    val fields: List<FormField>,
    val incomeCurrency: String = "RUB",
    val amountCurrency: String = "RUB"
)
