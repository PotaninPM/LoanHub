package com.potaninpm.navigation

import kotlinx.serialization.Serializable

sealed class RootNavDestinations {

    @Serializable
    data class LoanApplicationScreen(
        val loanType: String,
        val draftId: String? = null,
        val importedFieldsJson: String? = null
    ) : RootNavDestinations()

    @Serializable
    data class LoanApplicationPreviewScreen(
        val fieldsJson: String,
        val incomeCurrency: String,
        val amountCurrency: String,
        val loanType: String
    ) : RootNavDestinations()

    @Serializable
    data class ApplicationDetailsScreen(
        val applicationId: String
    ) : RootNavDestinations()

    @Serializable
    data class LoanDetailsScreen(
        val loanId: String,
        val startColor: String,
        val endColor: String
    ) : RootNavDestinations()

    @Serializable
    data object EditProfileScreen : RootNavDestinations()

    @Serializable
    data object LoginScreen : RootNavDestinations()

    @Serializable
    object MainFlow : RootNavDestinations()
}