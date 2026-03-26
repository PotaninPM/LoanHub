package com.potaninpm.feature_application_details.domain.logic

import com.potaninpm.feature_application_details.presentation.utils.ProbabilityLevel
import kotlin.math.pow

class ProbabilityCalculator {

    fun calculate(type: String, income: Double, amount: Double, term: Double): Triple<ProbabilityLevel, Int, Int> {
        if (type == CREDIT_CARD) return Triple(ProbabilityLevel.UNKNOWN, 0, 0)
        
        if (income == 0.0 || term == 0.0) {
            return Triple(ProbabilityLevel.UNKNOWN, 0, 0)
        }

        val i = 0.10 / 12

        val factor = (1 + i).pow(-term)
        val payment = if (1 - factor != 0.0) amount * (i / (1 - factor)) else amount / term

        val N = (payment / income) * 100
        val nInt = N.toInt()

        val level = when {
            nInt <= 40 -> ProbabilityLevel.HIGH
            nInt < 50 -> ProbabilityLevel.MEDIUM
            else -> ProbabilityLevel.LOW
        }

        return Triple(level, nInt, payment.toInt())
    }

    private companion object {
        const val CREDIT_CARD = "credit_card"
    }
}
