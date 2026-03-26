package com.potaninpm.feature_loan_details.data.repository

import com.potaninpm.feature_loan_details.data.mapper.toDomain
import com.potaninpm.feature_loan_details.domain.repository.LoanDetailsRepository
import com.potaninpm.feature_loan_details.utils.LoanDetails
import com.potaninpm.network.supabase.loans.LoansSupabaseApi

class LoanDetailsRepositoryImpl(
    private val api: LoansSupabaseApi
) : LoanDetailsRepository {

    override suspend fun getLoanDetails(productId: String): Result<LoanDetails> = runCatching {
        val response = api.getLoanDetails(productId = "eq.$productId")
        if (response.isNotEmpty()) {
            response.first().toDomain()
        } else {
            throw Exception("Product not found")
        }
    }
}
