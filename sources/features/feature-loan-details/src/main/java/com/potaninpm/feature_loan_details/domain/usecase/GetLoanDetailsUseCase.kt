package com.potaninpm.feature_loan_details.domain.usecase

import com.potaninpm.feature_loan_details.domain.repository.LoanDetailsRepository
import com.potaninpm.feature_loan_details.utils.LoanDetails

class GetLoanDetailsUseCase(
    private val repository: LoanDetailsRepository
) {
    suspend operator fun invoke(productId: String): Result<LoanDetails> {
        return repository.getLoanDetails(productId)
    }
}
