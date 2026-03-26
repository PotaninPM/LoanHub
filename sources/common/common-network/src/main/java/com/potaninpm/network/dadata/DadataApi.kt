package com.potaninpm.network.dadata

import com.potaninpm.network.dadata.dto.DadataRequest
import com.potaninpm.network.dadata.dto.DadataResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DadataApi {
    @POST("suggestions/api/4_1/rs/suggest/fio")
    suspend fun suggestFio(
        @Header("Content-Type") type: String = "application/json",
        @Body query: DadataRequest
    ): DadataResponse

    @POST("suggestions/api/4_1/rs/suggest/party")
    suspend fun suggestParty(
        @Body query: DadataRequest
    ): DadataResponse

    @POST("suggestions/api/4_1/rs/suggest/address")
    suspend fun suggestAddress(
        @Body query: DadataRequest
    ): DadataResponse
}
