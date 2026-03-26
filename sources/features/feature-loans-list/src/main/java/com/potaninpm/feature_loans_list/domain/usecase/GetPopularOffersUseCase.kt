package com.potaninpm.feature_loans_list.domain.usecase

import com.potaninpm.feature_loans_list.domain.repository.LoanProductsRepository
import com.potaninpm.feature_loans_list.utils.LoanType

class GetPopularOffersUseCase(
    private val repository: LoanProductsRepository
) {
    suspend operator fun invoke(): Result<List<LoanType>> {
        return repository.getPopularOffers()
    }
}
