package com.potaninpm.feature_my_requests.utils

import com.potaninpm.database.models.LoanApplication

internal fun LoanApplication.buildPreviewText(): String {
    val fio = fields.find { it.id == "fio" || it.id == "surname" }?.value.orEmpty()
    val amount = fields.find { it.id == "amount" }?.value.orEmpty()
    return listOfNotNull(
        fio.takeIf { it.isNotBlank() },
        if (amount.isNotBlank()) "$amount $amountCurrency" else null
    ).joinToString(" · ")
}
