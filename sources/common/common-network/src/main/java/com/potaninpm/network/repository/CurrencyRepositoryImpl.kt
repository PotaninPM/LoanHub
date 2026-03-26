package com.potaninpm.network.repository

import com.potaninpm.network.ApiConstants
import com.potaninpm.network.rates.UnirateApi

class CurrencyRepositoryImpl(
    private val unirateApi: UnirateApi
) : CurrencyRepository {

    override suspend fun convertCurrency(amount: Double, from: String, to: String): Result<Double> = runCatching {
        if (from == to) return@runCatching amount
        
        val response = unirateApi.getRates(apiKey = ApiConstants.UNIRATE_API_KEY, from = from, to = to)
        val rate = response.rate ?: response.rates?.get(to) ?: 1.0
        amount * rate
    }
}
