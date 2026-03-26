package com.potaninpm.feature_loans_list.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import com.potaninpm.feature_loans_list.R
import com.potaninpm.feature_loans_list.utils.LoanType

val DEFAULT_LOAN_TYPES = listOf(
    LoanType(
        id = "mortgage",
        title = "Ипотека",
        description = "Покупка жилья по низкой ставке",
        icon = Icons.Default.Home,
        image = R.drawable.mortgage,
        gradientStart = Color(0xFF059669),
        gradientEnd = Color(0xFF0D9488)
    ),
    LoanType(
        id = "consumer",
        title = "Потребительский кредит",
        description = "Наличные на любые цели",
        icon = Icons.Default.Person,
        image = R.drawable.consumer,
        gradientEnd = Color(0xFF7C3AED),
        gradientStart = Color(0xFF544CE1)
    ),
    LoanType(
        id = "auto",
        title = "Автокредит",
        image = R.drawable.auto,
        description = "Новые и подержанные авто",
        icon = Icons.Default.DirectionsCar,
        gradientStart = Color(0xFFD97706),
        gradientEnd = Color(0xFFDC2626)
    ),
    LoanType(
        id = "credit_card",
        title = "Кредитная карта",
        description = "До 120 дней без процентов",
        icon = Icons.Default.CreditCard,
        image = R.drawable.save,
        gradientStart = Color(0xFF2563EB),
        gradientEnd = Color(0xFF7C3AED)
    ),
    LoanType(
        id = "leasing",
        title = "Лизинг",
        description = "Оборудование и транспорт",
        icon = Icons.Default.Handshake,
        image = R.drawable.leasing,
        gradientStart = Color(0xFF0EA5E9),
        gradientEnd = Color(0xFF06B6D4)
    )
)