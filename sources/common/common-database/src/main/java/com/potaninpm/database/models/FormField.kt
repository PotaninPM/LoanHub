package com.potaninpm.database.models

import kotlinx.serialization.Serializable

@Serializable
data class FormField(
    val id: String,
    val labelRes: Int = 0,
    val value: String = "",
    val type: FieldType = FieldType.TEXT,
    val isRequired: Boolean = true,
    val error: String? = null,
    val suggestions: List<String> = emptyList()
)

enum class FieldType {
    TEXT, NUMBER, DATE, SUGGEST_FIO, SUGGEST_ADDRESS, SUGGEST_ORG
}
