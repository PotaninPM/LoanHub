package com.potaninpm.feature_loan_application.domain.validation

import com.potaninpm.database.models.FormField
import com.potaninpm.feature_loan_application.presentation.models.LoanField
import com.potaninpm.uikit.presentation.inputs.LoanHubUiTextFieldValidation

class LoanFieldValidator {

    fun getValidation(field: FormField): LoanHubUiTextFieldValidation? {
        if (!field.isRequired) return null

        return when (field.id) {
            LoanField.EMAIL.key -> LoanHubUiTextFieldValidation.Email()
            LoanField.INCOME.key, 
            LoanField.AMOUNT.key,
            LoanField.TERM.key,
            LoanField.ADVANCE.key -> LoanHubUiTextFieldValidation.Money()
            LoanField.PHONE.key -> LoanHubUiTextFieldValidation.Phone()
            LoanField.PASSPORT_DATE.key -> LoanHubUiTextFieldValidation.Date()
            LoanField.INN.key,
            LoanField.ORG_INN.key -> LoanHubUiTextFieldValidation.Regex(
                pattern = "^\\d{10,12}$",
                errorMessage = "ИНН должен содержать 10 или 12 цифр"
            )
            LoanField.SNILS.key -> LoanHubUiTextFieldValidation.Regex(
                pattern = "^\\d{3}-\\d{3}-\\d{3} \\d{2}$",
                errorMessage = "Формат: XXX-XXX-XXX XX"
            )
            LoanField.PASSPORT.key -> LoanHubUiTextFieldValidation.Regex(
                pattern = "^\\d{4} \\d{6}$",
                errorMessage = "Формат: 1234 567890"
            )
            LoanField.PASSPORT_CODE.key -> LoanHubUiTextFieldValidation.Regex(
                pattern = "^\\d{3}-\\d{3}$",
                errorMessage = "Формат: 123-456"
            )
            else -> LoanHubUiTextFieldValidation.Required()
        }
    }
}
