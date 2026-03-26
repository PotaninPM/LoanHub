package com.potaninpm.feature_loan_application.presentation.models

enum class LoanField(val key: String) {
    FIO("fio"),
    PASSPORT("passport"),
    PASSPORT_ISSUED("passport_issued"),
    PASSPORT_DATE("passport_date"),
    PASSPORT_CODE("passport_code"),
    INN("inn"),
    ADDRESS("address"),
    SNILS("snils"),
    EMAIL("email"),
    PHONE("phone"),
    ZIP("zip"),
    ORG_NAME("org_name"),
    ORG_INN("org_inn"),
    INCOME("income"),
    AMOUNT("amount"),
    TERM("term"),
    OBJ_ADDRESS("obj_address"),
    CAR_BRAND("car_brand"),
    PROPERTY_TYPE("property_type"),
    ADVANCE("advance"),
    SURNAME("surname"),
    NAME("name"),
    PATRONYMIC("patronymic")
}
