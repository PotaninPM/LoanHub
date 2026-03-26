package com.potaninpm.feature_loan_application.domain.repository

import com.potaninpm.network.dadata.dto.DadataSuggestion

interface SuggestionRepository {
    suspend fun suggestFio(query: String): Result<List<DadataSuggestion>>
    suspend fun suggestParty(query: String): Result<List<DadataSuggestion>>
    suspend fun suggestAddress(query: String): Result<List<DadataSuggestion>>
}
