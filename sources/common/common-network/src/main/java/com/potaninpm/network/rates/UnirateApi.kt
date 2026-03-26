package com.potaninpm.network.rates

import com.potaninpm.network.rates.dto.UnirateResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UnirateApi {
    @GET("rates")
    suspend fun getRates(
        @Query("api_key") apiKey: String,
        @Query("from") from: String,
        @Query("to") to: String? = null
    ): UnirateResponse
}
