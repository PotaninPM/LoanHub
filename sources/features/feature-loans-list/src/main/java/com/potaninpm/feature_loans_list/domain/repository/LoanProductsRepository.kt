package com.potaninpm.feature_loans_list.domain.repository

import com.potaninpm.feature_loans_list.utils.LoanType

interface LoanProductsRepository {
    suspend fun getAllProducts(): Result<List<LoanType>>
    suspend fun getPopularOffers(): Result<List<LoanType>>
}
