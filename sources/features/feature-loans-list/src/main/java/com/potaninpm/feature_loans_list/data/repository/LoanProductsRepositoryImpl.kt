package com.potaninpm.feature_loans_list.data.repository

import com.potaninpm.feature_loans_list.data.mapper.toDomainList
import com.potaninpm.feature_loans_list.domain.repository.LoanProductsRepository
import com.potaninpm.feature_loans_list.utils.LoanType
import com.potaninpm.network.supabase.loans.LoansSupabaseApi

class LoanProductsRepositoryImpl(
    private val api: LoansSupabaseApi
) : LoanProductsRepository {

    override suspend fun getAllProducts(): Result<List<LoanType>> = runCatching {
        api.getAllProducts().toDomainList()
    }

    override suspend fun getPopularOffers(): Result<List<LoanType>> = runCatching {
        api.getPopularOffers().toDomainList()
    }
}
