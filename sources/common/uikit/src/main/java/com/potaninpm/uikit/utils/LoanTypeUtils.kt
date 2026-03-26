package com.potaninpm.uikit.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.potaninpm.uikit.R

@StringRes
fun loanTypeTitle(type: String): Int {
    return when (type) {
        "consumer" -> R.string.loan_type_consumer
        "mortgage" -> R.string.loan_type_mortgage
        "auto" -> R.string.loan_type_auto
        "credit_card" -> R.string.loan_type_credit_card
        "leasing" -> R.string.loan_type_leasing
        else -> R.string.loan_type_consumer
    }
}

fun loanTypeIcon(type: String): ImageVector {
    return when (type) {
        "consumer" -> Icons.Default.Person
        "mortgage" -> Icons.Default.Home
        "auto" -> Icons.Default.DirectionsCar
        "credit_card" -> Icons.Default.CreditCard
        "leasing" -> Icons.Default.Handshake
        else -> Icons.Default.AccountBalance
    }
}

fun loanTypeGradient(type: String): Pair<Color, Color> {
    return when (type) {
        "consumer" -> Color(0xFF6366F1) to Color(0xFF818CF8)
        "mortgage" -> Color(0xFF0EA5E9) to Color(0xFF38BDF8)
        "auto" -> Color(0xFFF59E0B) to Color(0xFFFBBF24)
        "credit_card" -> Color(0xFFEC4899) to Color(0xFFF472B6)
        "leasing" -> Color(0xFF10B981) to Color(0xFF34D399)
        else -> Color(0xFF8B5CF6) to Color(0xFFA78BFA)
    }
}
