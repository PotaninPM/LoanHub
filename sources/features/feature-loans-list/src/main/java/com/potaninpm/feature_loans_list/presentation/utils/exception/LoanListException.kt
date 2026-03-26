package com.potaninpm.feature_loans_list.presentation.utils.exception

data class LoanListException(
    override val message: String,
    val exception: Throwable? = null
) : Exception(message, exception)