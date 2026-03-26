package com.potaninpm.feature_loan_application.domain.usecase

import com.potaninpm.database.models.FieldType
import com.potaninpm.feature_loan_application.domain.repository.SuggestionRepository
import com.potaninpm.network.dadata.dto.DadataSuggestion

class GetSuggestionUseCase(
    private val repository: SuggestionRepository
) {
    suspend operator fun invoke(query: String, type: FieldType): Result<List<DadataSuggestion>> {
        if (query.length < 2) return Result.success(emptyList())

        return when (type) {
            FieldType.SUGGEST_FIO -> repository.suggestFio(query)
            FieldType.SUGGEST_ORG -> repository.suggestParty(query)
            FieldType.SUGGEST_ADDRESS -> repository.suggestAddress(query)
            else -> Result.success(emptyList())
        }
    }
}
