package com.potaninpm.feature_loan_details.data.mapper

import com.potaninpm.feature_loan_details.utils.BorrowerRequirements
import com.potaninpm.feature_loan_details.utils.CreditType
import com.potaninpm.feature_loan_details.utils.Currency
import com.potaninpm.feature_loan_details.utils.LoanDetails
import com.potaninpm.feature_loan_details.utils.MainConditions
import com.potaninpm.network.supabase.loans.models.BorrowerRequirementsDto
import com.potaninpm.network.supabase.loans.models.LoanDetailsDto
import com.potaninpm.network.supabase.loans.models.MainConditionsDto

internal fun LoanDetailsDto.toDomain(): LoanDetails {
    return LoanDetails(
        type = mapCreditType(type),
        title = title,
        description = description,
        mainConditions = mainConditions.toDomain(),
        borrowerRequirements = borrowerRequirements.toDomain()
    )
}

internal fun MainConditionsDto.toDomain(): MainConditions {
    return MainConditions(
        minAmount = minAmount,
        maxAmount = maxAmount,
        minTermInMonths = minTermInMonths,
        maxTermInMonths = maxTermInMonths,
        interestRateFrom = interestRateFrom,
        currency = mapCurrency(currency),
        initialPaymentPercentFrom = initialPaymentPercentFrom,
        gracePeriodDays = gracePeriodDays,
        minMonthlyPaymentPercent = minMonthlyPaymentPercent
    )
}

internal fun BorrowerRequirementsDto.toDomain(): BorrowerRequirements {
    return BorrowerRequirements(
        minAge = minAge,
        maxAge = maxAge,
        citizenshipRequired = citizenshipRequired,
        minWorkExperienceMonths = minWorkExperienceMonths,
        incomeConfirmationRequired = incomeConfirmationRequired
    )
}

private fun mapCreditType(type: String): CreditType {
    return try {
        CreditType.valueOf(type.uppercase())
    } catch (e: Exception) {
        CreditType.CONSUMER
    }
}

private fun mapCurrency(currency: String): Currency {
    return try {
        Currency.valueOf(currency.uppercase())
    } catch (e: Exception) {
        Currency.RUB
    }
}
