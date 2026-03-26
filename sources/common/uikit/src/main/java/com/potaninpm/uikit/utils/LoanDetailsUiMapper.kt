package com.potaninpm.uikit.utils

import com.potaninpm.database.models.LoanApplication
import com.potaninpm.uikit.presentation.details.DetailItem
import com.potaninpm.utils.currency.getCurrencySymbol

class LoanDetailsUiMapper {

    fun mapToDetailItems(
        application: LoanApplication,
        convertedIncome: Int? = null,
        convertedAmount: Int? = null
    ): List<DetailItem> {
        return application.fields.map { field ->
            val currencySymbol = when (field.id) {
                INCOME -> getCurrencySymbol(application.incomeCurrency)
                AMOUNT -> getCurrencySymbol(application.amountCurrency)
                else -> ""
            }

            val displayValue = if (field.value.isNotEmpty()) {
                if (currencySymbol.isNotEmpty()) "${field.value} $currencySymbol" else field.value
            } else "-"

            val convertedValue = when (field.id) {
                INCOME -> if (application.incomeCurrency != RUB) convertedIncome?.let { "$it ₽" } else null
                AMOUNT -> if (application.amountCurrency != RUB) convertedAmount?.let { "$it ₽" } else null
                else -> null
            }

            DetailItem(
                labelRes = field.labelRes,
                value = displayValue,
                convertedValue = convertedValue
            )
        }
    }

    private companion object {
        const val INCOME = "income"
        const val AMOUNT = "amount"
        const val RUB = "RUB"
    }
}
