package com.potaninpm.network.supabase.profile.models

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("patronymic") val patronymic: String? = null,
    @SerializedName("birth_date") val birthDate: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("email") val email: String? = null,

    @SerializedName("passport_series_number") val passportSeriesNumber: String? = null,
    @SerializedName("passport_issued_by") val passportIssuedBy: String? = null,
    @SerializedName("passport_issue_date") val passportIssueDate: String? = null,
    @SerializedName("passport_division_code") val passportDivisionCode: String? = null,

    @SerializedName("address_registration") val addressRegistration: String? = null,
    @SerializedName("postal_code") val postalCode: String? = null,
    @SerializedName("inn") val inn: String? = null,
    @SerializedName("snils") val snils: String? = null,

    @SerializedName("employer_name") val employerName: String? = null,
    @SerializedName("employer_inn") val employerInn: String? = null,
    @SerializedName("job_title") val jobTitle: String? = null,
    @SerializedName("work_experience") val workExperience: String? = null,
    @SerializedName("monthly_income") val monthlyIncome: Int? = null,

    @SerializedName("role") val role: String? = null
)