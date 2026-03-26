package com.potaninpm.feature_loan_details.utils

fun formatAmount(amount: Long): String {
    return String.format("%,d", amount).replace(',', ' ') + " ₽"
}
