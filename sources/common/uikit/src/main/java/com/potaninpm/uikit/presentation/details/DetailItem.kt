package com.potaninpm.uikit.presentation.details

data class DetailItem(
    val labelRes: Int,
    val value: String,
    val convertedValue: String? = null
)