package com.potaninpm.feature_loan_application.domain.factory

import com.potaninpm.database.models.FieldType
import com.potaninpm.database.models.FormField
import com.potaninpm.feature_loan_application.R
import com.potaninpm.feature_loan_application.presentation.models.LoanField

class LoanFormFactory {

    fun createForm(loanType: String): List<FormField> {
        return when (loanType) {
            CONSUMER -> createConsumerForm()
            MORTGAGE -> createMortgageForm()
            AUTO -> createAutoForm()
            CREDIT_CARD -> createCreditCardForm()
            LEASING -> createLeasingForm()
            else -> emptyList()
        }
    }

    private fun createConsumerForm() = listOf(
        FormField(LoanField.FIO.key, labelRes = R.string.field_fio, type = FieldType.SUGGEST_FIO),
        FormField(LoanField.PASSPORT.key, labelRes = R.string.field_passport),
        FormField(LoanField.PASSPORT_ISSUED.key, labelRes = R.string.field_passport_issued),
        FormField(LoanField.PASSPORT_DATE.key, labelRes = R.string.field_passport_date, type = FieldType.DATE),
        FormField(LoanField.PASSPORT_CODE.key, labelRes = R.string.field_passport_code),
        FormField(LoanField.INN.key, labelRes = R.string.field_inn),
        FormField(LoanField.ADDRESS.key, labelRes = R.string.field_address, type = FieldType.SUGGEST_ADDRESS),
        FormField(LoanField.SNILS.key, labelRes = R.string.field_snils),
        FormField(LoanField.EMAIL.key, labelRes = R.string.field_email),
        FormField(LoanField.PHONE.key, labelRes = R.string.field_phone),
        FormField(LoanField.ORG_NAME.key, labelRes = R.string.field_org_name, type = FieldType.SUGGEST_ORG),
        FormField(LoanField.ORG_INN.key, labelRes = R.string.field_org_inn),
        FormField(LoanField.INCOME.key, labelRes = R.string.field_income, type = FieldType.NUMBER),
        FormField(LoanField.AMOUNT.key, labelRes = R.string.field_amount, type = FieldType.NUMBER),
        FormField(LoanField.TERM.key, labelRes = R.string.field_term, type = FieldType.NUMBER)
    )

    private fun createMortgageForm() = listOf(
        FormField(LoanField.FIO.key, labelRes = R.string.field_fio, type = FieldType.SUGGEST_FIO),
        FormField(LoanField.PASSPORT.key, labelRes = R.string.field_passport),
        FormField(LoanField.PASSPORT_ISSUED.key, labelRes = R.string.field_passport_issued),
        FormField(LoanField.PASSPORT_DATE.key, labelRes = R.string.field_passport_date, type = FieldType.DATE),
        FormField(LoanField.PASSPORT_CODE.key, labelRes = R.string.field_passport_code),
        FormField(LoanField.INN.key, labelRes = R.string.field_inn),
        FormField(LoanField.ADDRESS.key, labelRes = R.string.field_address, type = FieldType.SUGGEST_ADDRESS),
        FormField(LoanField.SNILS.key, labelRes = R.string.field_snils),
        FormField(LoanField.EMAIL.key, labelRes = R.string.field_email),
        FormField(LoanField.PHONE.key, labelRes = R.string.field_phone),
        FormField(LoanField.ORG_NAME.key, labelRes = R.string.field_org_name, type = FieldType.SUGGEST_ORG),
        FormField(LoanField.ORG_INN.key, labelRes = R.string.field_org_inn),
        FormField(LoanField.INCOME.key, labelRes = R.string.field_income, type = FieldType.NUMBER),
        FormField(LoanField.OBJ_ADDRESS.key, labelRes = R.string.field_obj_address, type = FieldType.SUGGEST_ADDRESS),
        FormField(LoanField.AMOUNT.key, labelRes = R.string.field_amount, type = FieldType.NUMBER),
        FormField(LoanField.TERM.key, labelRes = R.string.field_term, type = FieldType.NUMBER)
    )

    private fun createAutoForm() = listOf(
        FormField(LoanField.NAME.key, labelRes = R.string.field_name),
        FormField(LoanField.SURNAME.key, labelRes = R.string.field_surname),
        FormField(LoanField.PATRONYMIC.key, labelRes = R.string.field_patronymic),
        FormField(LoanField.PASSPORT.key, labelRes = R.string.field_passport),
        FormField(LoanField.PASSPORT_ISSUED.key, labelRes = R.string.field_passport_issued),
        FormField(LoanField.PASSPORT_DATE.key, labelRes = R.string.field_passport_date, type = FieldType.DATE),
        FormField(LoanField.PASSPORT_CODE.key, labelRes = R.string.field_passport_code),
        FormField(LoanField.INN.key, labelRes = R.string.field_inn),
        FormField(LoanField.ADDRESS.key, labelRes = R.string.field_address, type = FieldType.SUGGEST_ADDRESS),
        FormField(LoanField.SNILS.key, labelRes = R.string.field_snils),
        FormField(LoanField.EMAIL.key, labelRes = R.string.field_email),
        FormField(LoanField.PHONE.key, labelRes = R.string.field_phone),
        FormField(LoanField.ORG_NAME.key, labelRes = R.string.field_org_name, type = FieldType.SUGGEST_ORG),
        FormField(LoanField.ORG_INN.key, labelRes = R.string.field_org_inn),
        FormField(LoanField.INCOME.key, labelRes = R.string.field_income, type = FieldType.NUMBER),
        FormField(LoanField.CAR_BRAND.key, labelRes = R.string.field_car_brand),
        FormField(LoanField.AMOUNT.key, labelRes = R.string.field_amount, type = FieldType.NUMBER),
        FormField(LoanField.TERM.key, labelRes = R.string.field_term, type = FieldType.NUMBER)
    )

    private fun createCreditCardForm() = listOf(
        FormField(LoanField.FIO.key, labelRes = R.string.field_fio, type = FieldType.SUGGEST_FIO),
        FormField(LoanField.PASSPORT.key, labelRes = R.string.field_passport),
        FormField(LoanField.PASSPORT_ISSUED.key, labelRes = R.string.field_passport_issued),
        FormField(LoanField.PASSPORT_DATE.key, labelRes = R.string.field_passport_date, type = FieldType.DATE),
        FormField(LoanField.PASSPORT_CODE.key, labelRes = R.string.field_passport_code),
        FormField(LoanField.INN.key, labelRes = R.string.field_inn),
        FormField(LoanField.ADDRESS.key, labelRes = R.string.field_address, type = FieldType.SUGGEST_ADDRESS),
        FormField(LoanField.SNILS.key, labelRes = R.string.field_snils),
        FormField(LoanField.ZIP.key, labelRes = R.string.field_zip),
        FormField(LoanField.PHONE.key, labelRes = R.string.field_phone),
        FormField(LoanField.ORG_NAME.key, labelRes = R.string.field_org_name, type = FieldType.SUGGEST_ORG),
        FormField(LoanField.ORG_INN.key, labelRes = R.string.field_org_inn),
        FormField(LoanField.INCOME.key, labelRes = R.string.field_income, type = FieldType.NUMBER)
    )

    private fun createLeasingForm() = listOf(
        FormField(LoanField.FIO.key, labelRes = R.string.field_fio, type = FieldType.SUGGEST_FIO),
        FormField(LoanField.PASSPORT.key, labelRes = R.string.field_passport),
        FormField(LoanField.PASSPORT_ISSUED.key, labelRes = R.string.field_passport_issued),
        FormField(LoanField.PASSPORT_DATE.key, labelRes = R.string.field_passport_date, type = FieldType.DATE),
        FormField(LoanField.PASSPORT_CODE.key, labelRes = R.string.field_passport_code),
        FormField(LoanField.INN.key, labelRes = R.string.field_inn),
        FormField(LoanField.ADDRESS.key, labelRes = R.string.field_address, type = FieldType.SUGGEST_ADDRESS),
        FormField(LoanField.SNILS.key, labelRes = R.string.field_snils),
        FormField(LoanField.EMAIL.key, labelRes = R.string.field_email),
        FormField(LoanField.PHONE.key, labelRes = R.string.field_phone),
        FormField(LoanField.ORG_NAME.key, labelRes = R.string.field_org_name, type = FieldType.SUGGEST_ORG),
        FormField(LoanField.ORG_INN.key, labelRes = R.string.field_org_inn),
        FormField(LoanField.INCOME.key, labelRes = R.string.field_income, type = FieldType.NUMBER),
        FormField(LoanField.PROPERTY_TYPE.key, labelRes = R.string.field_property_type),
        FormField(LoanField.AMOUNT.key, labelRes = R.string.field_property_cost, type = FieldType.NUMBER),
        FormField(LoanField.TERM.key, labelRes = R.string.field_leasing_term, type = FieldType.NUMBER),
        FormField(LoanField.ADVANCE.key, labelRes = R.string.field_advance, type = FieldType.NUMBER)
    )

    private companion object {
        const val CONSUMER = "consumer"
        const val MORTGAGE = "mortgage"
        const val AUTO = "auto"
        const val CREDIT_CARD = "credit_card"
        const val LEASING = "leasing"
    }
}
