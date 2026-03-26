package com.potaninpm.feature_loan_application.data.repository

import com.potaninpm.feature_loan_application.domain.repository.SuggestionRepository
import com.potaninpm.network.dadata.DadataApi
import com.potaninpm.network.dadata.dto.DadataRequest
import com.potaninpm.network.dadata.dto.DadataSuggestion

class SuggestionRepositoryImpl(
    private val api: DadataApi
) : SuggestionRepository {

    override suspend fun suggestFio(query: String): Result<List<DadataSuggestion>> {
        return runCatching {
            api.suggestFio(
                query = DadataRequest(query)
            ).suggestions
        }
    }

    override suspend fun suggestParty(query: String): Result<List<DadataSuggestion>> {
        return runCatching {
            api.suggestParty(DadataRequest(query)).suggestions
        }
    }

    override suspend fun suggestAddress(query: String): Result<List<DadataSuggestion>> {
        return runCatching {
            api.suggestAddress(DadataRequest(query)).suggestions
        }
    }
}
