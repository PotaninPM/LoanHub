package com.potaninpm.network.repository

interface CurrencyRepository {
    suspend fun convertCurrency(amount: Double, from: String, to: String): Result<Double>
}
