package com.potaninpm.uikit.presentation.inputs

import android.icu.text.SimpleDateFormat
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.potaninpm.uikit.R
import com.potaninpm.uikit.presentation.text.LoanHubUiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoanHubUiTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    label: String? = null,
    error: String? = null,
    isSecured: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    validationType: LoanHubUiTextFieldValidation? = null,
    animatedSize: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValidationStatusChanged: (LoanHubUiTextFieldValidationState) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    var isSecuredContentVisible by remember { mutableStateOf(false) }
    var lastError by remember { mutableStateOf("") }
    var hasUserInteracted by rememberSaveable { mutableStateOf(value.isNotEmpty()) }

    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val resolvedValidationError = validationType?.let { resolveValidationError(value, it) }
    val validationError = if (hasUserInteracted) resolvedValidationError else null
    val currentError = error ?: validationError

    val errorAlpha by animateFloatAsState(
        targetValue = if (currentError != null) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    LaunchedEffect(value) {
        if (value.isNotEmpty()) {
            hasUserInteracted = true
        }
    }

    LaunchedEffect(validationType, resolvedValidationError) {
        if (validationType != null) {
            onValidationStatusChanged(
                LoanHubUiTextFieldValidationState(
                    isValid = resolvedValidationError == null,
                    errorMessage = resolvedValidationError
                )
            )
        }
    }

    LaunchedEffect(currentError) {
        if (currentError != null) {
            lastError = currentError
            if (isFocused) {
                bringIntoViewRequester.bringIntoView()
            }
        }
    }

    var textFieldValueState by remember { 
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    LaunchedEffect(value) {
        if (value != textFieldValueState.text) {
            textFieldValueState = TextFieldValue(text = value, selection = TextRange(value.length))
        }
    }

    Column(
        Modifier
            .then(
                if (animatedSize) Modifier.animateContentSize()
                else Modifier
            )
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        TextField(
            value = textFieldValueState,
            onValueChange = { newValue ->
                if (!hasUserInteracted) {
                    hasUserInteracted = true
                }
                textFieldValueState = newValue
                if (newValue.text != value) {
                    onValueChange(newValue.text)
                }
            },
            label = label?.let {
                @Composable {
                    Text(
                        text = label,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                    )
                }
            },
            placeholder = placeholder?.let {
                @Composable {
                    LoanHubUiText(
                        text = placeholder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (singleLine) 1 else Int.MAX_VALUE
                    )
                }
            },
            singleLine = singleLine,
            isError = currentError != null,
            visualTransformation = if (isSecured && !isSecuredContentVisible) PasswordVisualTransformation() else VisualTransformation.None,
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                errorTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            modifier = modifier
                .onFocusEvent {
                    isFocused = it.isFocused

                    if (it.isFocused) {
                        coroutineScope.launch {
                            delay(10)
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }
                .border(
                    if (isFocused) 2.dp else 1.dp,
                    when {
                        currentError != null -> MaterialTheme.colorScheme.error
                        isFocused -> MaterialTheme.colorScheme.primary
                        else -> Color.Gray.copy(0.5f)
                    },
                    shape = shape
                )
                .clip(shape)
                .background(MaterialTheme.colorScheme.surface),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = shape,
            trailingIcon = {
                if (isSecured) {
                    val image = if (isSecuredContentVisible) {
                        painterResource(id = R.drawable.visible)
                    } else {
                        painterResource(id = R.drawable.invisible)
                    }
                    IconButton(onClick = { isSecuredContentVisible = !isSecuredContentVisible }) {
                        Icon(
                            painter = image,
                            contentDescription = null,
                            tint = if (currentError != null) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            },
            keyboardOptions = keyboardOptions.copy(imeAction = ImeAction.Done),
            enabled = enabled
        )

        currentError?.let {
            LoanHubUiText(
                text = lastError,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .alpha(errorAlpha)
                    .padding(top = 4.dp)
                    .padding(start = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun resolveValidationError(
    value: String,
    validationType: LoanHubUiTextFieldValidation
): String? {
    return when (validationType) {
        is LoanHubUiTextFieldValidation.Required -> {
            if (value.isEmpty()) {
                validationType.errorMessage
                    ?: stringResource(R.string.prod_text_field_error_required)
            } else {
                null
            }
        }

        is LoanHubUiTextFieldValidation.Email -> {
            when {
                value.isEmpty() -> validationType.emptyError
                    ?: stringResource(R.string.prod_text_field_error_required)

                !EMAIL_REGEX.matches(value.trim()) -> validationType.invalidFormatError
                    ?: stringResource(R.string.prod_text_field_error_invalid_email)

                else -> null
            }
        }

        is LoanHubUiTextFieldValidation.Password -> validatePassword(value, validationType)

        is LoanHubUiTextFieldValidation.Custom -> validationType.validator(value)

        is LoanHubUiTextFieldValidation.Money -> validateMoney(value, validationType)

        is LoanHubUiTextFieldValidation.Regex -> {
            if (value.isEmpty()) return null
            if (!value.matches(validationType.pattern.toRegex())) {
                validationType.errorMessage ?: "Неверный формат"
            } else {
                null
            }
        }

        is LoanHubUiTextFieldValidation.Phone -> {
            if (value.isEmpty()) return null
            if (!value.matches("^\\+?[0-9]{10,12}$".toRegex())) {
                validationType.errorMessage ?: "Некорректный номер телефона"
            } else {
                null
            }
        }

        is LoanHubUiTextFieldValidation.Date -> {
             if (value.isEmpty()) return null
             try {
                 val sdf = SimpleDateFormat(validationType.pattern, java.util.Locale.getDefault())
                 sdf.isLenient = false
                 sdf.parse(value)
                 null
             } catch (e: Exception) {
                 validationType.errorMessage ?: "Неверная дата"
             }
        }
    }
}

@Composable
private fun validateMoney(
    value: String,
    validationType: LoanHubUiTextFieldValidation.Money
): String? {
    if (value.isEmpty()) {
        return validationType.emptyError
            ?: stringResource(R.string.prod_text_field_error_required)
    }

    if (value.length > validationType.maxLength) {

    }

    return null
}

@Composable
private fun validatePassword(
    value: String,
    validationType: LoanHubUiTextFieldValidation.Password
): String? {
    if (value.isEmpty()) {
        return validationType.emptyError
            ?: stringResource(R.string.prod_text_field_error_required)
    }

    if (value.length < validationType.minLength) {
        return validationType.minLengthError
            ?: stringResource(
                R.string.prod_text_field_error_password_length,
                validationType.minLength
            )
    }

    if (validationType.requireUppercase && value.none { it.isUpperCase() }) {
        return validationType.uppercaseError
            ?: stringResource(R.string.prod_text_field_error_password_uppercase)
    }

    if (validationType.requireLowercase && value.none { it.isLowerCase() }) {
        return validationType.lowercaseError
            ?: stringResource(R.string.prod_text_field_error_password_lowercase)
    }

    if (validationType.requireDigit && value.none { it.isDigit() }) {
        return validationType.digitError
            ?: stringResource(R.string.prod_text_field_error_password_digit)
    }

    if (validationType.requireSpecialCharacter && value.none { !it.isLetterOrDigit() }) {
        return validationType.specialCharacterError
            ?: stringResource(R.string.prod_text_field_error_password_special)
    }

    return null
}

private val EMAIL_REGEX =
    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
