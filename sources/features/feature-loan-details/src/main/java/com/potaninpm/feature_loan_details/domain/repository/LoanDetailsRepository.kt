package com.potaninpm.feature_loan_details.domain.repository

import com.potaninpm.feature_loan_details.utils.LoanDetails

interface LoanDetailsRepository {
    suspend fun getLoanDetails(productId: String): Result<LoanDetails>
}
