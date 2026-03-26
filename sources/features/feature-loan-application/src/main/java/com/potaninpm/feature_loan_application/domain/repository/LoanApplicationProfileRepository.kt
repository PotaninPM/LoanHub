package com.potaninpm.feature_loan_application.domain.repository

import com.potaninpm.feature_loan_application.domain.models.Profile

interface LoanApplicationProfileRepository {
    suspend fun getProfile(): Result<Profile>
}
