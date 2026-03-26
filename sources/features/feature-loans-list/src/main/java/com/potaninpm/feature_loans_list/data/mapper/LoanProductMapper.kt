package com.potaninpm.feature_loans_list.data.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.potaninpm.feature_loans_list.R
import com.potaninpm.feature_loans_list.utils.LoanType
import com.potaninpm.network.supabase.loans.models.LoanProductDto
import com.potaninpm.uikit.utils.parseHexColor

internal fun LoanProductDto.toDomain(): LoanType {
    return LoanType(
        id = id,
        title = title,
        description = description,
        icon = mapIconName(iconName),
        image = mapImageName(id),
        gradientStart = parseHexColor(gradientStart),
        gradientEnd = parseHexColor(gradientEnd)
    )
}

internal fun List<LoanProductDto>.toDomainList(): List<LoanType> {
    return map { it.toDomain() }
}



private fun mapIconName(name: String): ImageVector {
    return when (name) {
        "person" -> Icons.Default.Person
        "home" -> Icons.Default.Home
        "directions_car" -> Icons.Default.DirectionsCar
        "credit_card" -> Icons.Default.CreditCard
        "handshake" -> Icons.Default.Handshake
        "account_balance" -> Icons.Default.AccountBalance
        else -> Icons.Default.Star
    }
}

private fun mapImageName(id: String): Int? {
    return when (id) {
        "auto" -> R.drawable.auto
        "consumer" -> R.drawable.consumer
        "credit_card" -> R.drawable.save
        "leasing" -> R.drawable.leasing
        "mortgage" -> R.drawable.mortgage
        else -> null
    }
}
