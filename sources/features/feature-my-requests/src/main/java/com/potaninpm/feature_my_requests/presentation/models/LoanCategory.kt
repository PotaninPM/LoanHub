package com.potaninpm.feature_my_requests.presentation.models
import androidx.annotation.StringRes
import com.potaninpm.feature_my_requests.R

enum class LoanCategory(val id: String, @StringRes val labelResId: Int) {
    CONSUMER("consumer", R.string.filter_consumer),
    MORTGAGE("mortgage", R.string.filter_mortgage),
    AUTO("auto", R.string.filter_auto),
    CREDIT_CARD("credit_card", R.string.filter_credit_card),
    LEASING("leasing", R.string.filter_leasing);

    companion object {
        fun fromId(id: String?): LoanCategory? = entries.find { it.id == id }
    }
}
