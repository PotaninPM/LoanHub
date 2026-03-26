package com.potaninpm.feature_loan_application.data.repository

import com.potaninpm.feature_loan_application.domain.models.Profile
import com.potaninpm.feature_loan_application.domain.repository.LoanApplicationProfileRepository
import com.potaninpm.network.supabase.profile.ProfileSupabaseApi
import com.potaninpm.network.supabase.profile.models.UserProfileDto

class LoanApplicationProfileRepositoryImpl(
    private val api: ProfileSupabaseApi
) : LoanApplicationProfileRepository {

    override suspend fun getProfile(): Result<Profile> = runCatching {
        val profiles = api.getProfile()
        val dto = profiles.firstOrNull() ?: throw IllegalStateException("Profile not found")
        dto.toDomain()
    }

    private fun UserProfileDto.toDomain(): Profile = Profile(
        id = id.orEmpty(),
        firstName = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        patronymic = patronymic.orEmpty(),
        birthDate = birthDate.orEmpty(),
        phone = phone.orEmpty(),
        email = email.orEmpty(),
        passportSeriesNumber = passportSeriesNumber.orEmpty(),
        passportIssuedBy = passportIssuedBy.orEmpty(),
        passportIssueDate = passportIssueDate.orEmpty(),
        passportDivisionCode = passportDivisionCode.orEmpty(),
        addressRegistration = addressRegistration.orEmpty(),
        postalCode = postalCode.orEmpty(),
        inn = inn.orEmpty(),
        snils = snils.orEmpty(),
        employerName = employerName.orEmpty(),
        employerInn = employerInn.orEmpty(),
        jobTitle = jobTitle.orEmpty(),
        workExperience = workExperience.orEmpty(),
        monthlyIncome = monthlyIncome
    )
}
