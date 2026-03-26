package com.potaninpm.utils.currency

fun getCurrencySymbol(code: String): String {
    return when (code) {
        "RUB" -> "₽"
        "USD" -> "$"
        "EUR" -> "€"
        "CNY" -> "¥"
        else -> code
    }
}