package com.potaninpm.database.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loan_drafts")
data class LoanEntity(
    @PrimaryKey val id: String,
    val type: String,
    val date: String,
    val fieldsJson: String,
    val incomeCurrency: String,
    val amountCurrency: String,
    val status: String = "DRAFT"
)
