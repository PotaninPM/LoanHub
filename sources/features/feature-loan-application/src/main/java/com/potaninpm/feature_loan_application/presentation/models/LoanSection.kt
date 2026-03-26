package com.potaninpm.feature_loan_application.presentation.models

import com.potaninpm.feature_loan_application.R

enum class LoanSection(val titleRes: Int, val fields: Set<LoanField>) {
    PERSONAL(R.string.section_personal, setOf(LoanField.FIO, LoanField.NAME, LoanField.SURNAME, LoanField.PATRONYMIC)),
    PASSPORT(R.string.section_passport, setOf(LoanField.PASSPORT, LoanField.PASSPORT_ISSUED, LoanField.PASSPORT_DATE, LoanField.PASSPORT_CODE)),
    IDENTITY(R.string.section_identity, setOf(LoanField.INN, LoanField.ADDRESS, LoanField.SNILS, LoanField.EMAIL, LoanField.PHONE, LoanField.ZIP)),
    WORK(R.string.section_work, setOf(LoanField.ORG_NAME, LoanField.ORG_INN, LoanField.INCOME)),
    LOAN(R.string.section_loan, setOf(LoanField.AMOUNT, LoanField.TERM, LoanField.OBJ_ADDRESS, LoanField.CAR_BRAND, LoanField.PROPERTY_TYPE, LoanField.ADVANCE))
}
