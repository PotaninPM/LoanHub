package com.potaninpm.feature_loan_application.domain.models

data class Profile(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val patronymic: String = "",
    val birthDate: String = "",
    val phone: String = "",
    val email: String = "",

    val passportSeriesNumber: String = "",
    val passportIssuedBy: String = "",
    val passportIssueDate: String = "",
    val passportDivisionCode: String = "",

    val addressRegistration: String = "",
    val postalCode: String = "",
    val inn: String = "",
    val snils: String = "",

    val employerName: String = "",
    val employerInn: String = "",
    val jobTitle: String = "",
    val workExperience: String = "",
    val monthlyIncome: Int? = null,
)
