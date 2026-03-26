package com.potaninpm.feature_loan_application.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.potaninpm.database.models.FieldType
import com.potaninpm.database.models.FormField
import com.potaninpm.feature_loan_application.presentation.viewmodel.LoanApplicationViewModel
import com.potaninpm.uikit.presentation.inputs.LoanHubUiDatePicker
import com.potaninpm.uikit.presentation.inputs.LoanHubUiTextField
import com.potaninpm.uikit.presentation.inputs.LoanHubUiTextFieldValidation
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun FormFieldItem(
    field: FormField,
    validation: LoanHubUiTextFieldValidation?,
    viewModel: LoanApplicationViewModel,
    incomeCurrency: String,
    amountCurrency: String,
    currencies: List<String>
) {
    val label = stringResource(field.labelRes)
    Column {
        if (field.type == FieldType.DATE) {
            LoanHubUiDatePicker(
                value = field.value,
                onValueChange = { viewModel.onFieldChange(field.id, it) },
                label = label,
                error = field.error,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            val keyboardOptions = remember(validation, field.type, field.id) {
                when {
                    validation is LoanHubUiTextFieldValidation.Phone -> KeyboardOptions(keyboardType = KeyboardType.Phone)
                    validation is LoanHubUiTextFieldValidation.Money -> KeyboardOptions(keyboardType = KeyboardType.Number)
                    field.type == FieldType.NUMBER -> KeyboardOptions(keyboardType = KeyboardType.Number)

                    validation is LoanHubUiTextFieldValidation.Regex &&
                    (field.id == "inn" || field.id == "snils" || field.id == "passport" || field.id == "passport_code" || field.id == "org_inn") -> {
                        KeyboardOptions(keyboardType = KeyboardType.Number)
                    }
                    else -> KeyboardOptions.Default
                }
            }

            LoanHubUiTextField(
                value = field.value,
                onValueChange = { viewModel.onFieldChange(field.id, it) },
                label = label,
                error = field.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused) {
                            viewModel.onFieldFocusLost(field.id)
                        }
                    },
                singleLine = field.type != FieldType.TEXT,
                validationType = validation,
                keyboardOptions = keyboardOptions,
                onValidationStatusChanged = { state ->
                    viewModel.onValidationResult(field.id, state.isValid, state.errorMessage)
                }
            )
        }

        AnimatedVisibility(
            visible = field.suggestions.isNotEmpty(),
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 4.dp,
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
            ) {
                Column {
                    field.suggestions.forEach { suggestion ->
                        LoanHubUiText(
                            text = suggestion,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onSuggestionClick(field.id, suggestion)
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        if (field.id == "income" || field.id == "amount") {
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val currentCurrency = if (field.id == "income") incomeCurrency else amountCurrency
                currencies.forEach { currency ->
                    CurrencyChip(
                        currency = currency,
                        isSelected = currency == currentCurrency,
                        onClick = {
                            if (field.id == "income") viewModel.onIncomeCurrencyChange(currency)
                            else viewModel.onAmountCurrencyChange(currency)
                        }
                    )
                }
            }
        }
    }
}
