package com.potaninpm.feature_profile.data.mapper

import com.potaninpm.feature_profile.utils.Profile
import com.potaninpm.network.supabase.profile.models.UserProfileDto

fun UserProfileDto.toDomain(): Profile = Profile(
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
    monthlyIncome = monthlyIncome,

    role = role.orEmpty()
)