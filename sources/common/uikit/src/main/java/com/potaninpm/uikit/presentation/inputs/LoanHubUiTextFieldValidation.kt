package com.potaninpm.uikit.presentation.inputs

sealed class LoanHubUiTextFieldValidation {

    data class Required(
        val errorMessage: String? = null
    ) : LoanHubUiTextFieldValidation()

    data class Email(
        val emptyError: String? = null,
        val invalidFormatError: String? = null
    ) : LoanHubUiTextFieldValidation()

    data class Password(
        val minLength: Int = 8,
        val requireUppercase: Boolean = true,
        val requireLowercase: Boolean = true,
        val requireDigit: Boolean = true,
        val requireSpecialCharacter: Boolean = false,
        val emptyError: String? = null,
        val minLengthError: String? = null,
        val uppercaseError: String? = null,
        val lowercaseError: String? = null,
        val digitError: String? = null,
        val specialCharacterError: String? = null
    ) : LoanHubUiTextFieldValidation()

    data class Money(
        val maxLength: Long = 10,
        val emptyError: String? = null,
        val invalidFormatError: String? = null
    ) : LoanHubUiTextFieldValidation()

    data class Regex(
        val pattern: String,
        val errorMessage: String? = null
    ) : LoanHubUiTextFieldValidation()

    data class Phone(
        val errorMessage: String? = null
    ) : LoanHubUiTextFieldValidation()

    data class Date(
        val pattern: String = "dd.MM.yyyy",
        val errorMessage: String? = null
    ) : LoanHubUiTextFieldValidation()

    class Custom(
        val validator: (value: String) -> String?
    ) : LoanHubUiTextFieldValidation()
}

data class LoanHubUiTextFieldValidationState(
    val isValid: Boolean,
    val errorMessage: String?
)
