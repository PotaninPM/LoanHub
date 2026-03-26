package com.potaninpm.feature_loan_application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potaninpm.database.domain.LoanDraftsRepository
import com.potaninpm.database.models.FieldType
import com.potaninpm.database.models.FormField
import com.potaninpm.database.models.LoanApplication
import com.potaninpm.feature_loan_application.domain.factory.LoanFormFactory
import com.potaninpm.feature_loan_application.domain.repository.LoanApplicationProfileRepository
import com.potaninpm.feature_loan_application.domain.usecase.GetSuggestionUseCase
import com.potaninpm.feature_loan_application.domain.validation.LoanFieldValidator
import com.potaninpm.feature_loan_application.presentation.models.LoanField
import com.potaninpm.uikit.presentation.inputs.LoanHubUiTextFieldValidation
import com.potaninpm.utils.dispatchers.DefaultDispatcherProvider
import com.potaninpm.utils.dispatchers.DispatcherProvider
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.util.UUID

class LoanApplicationViewModel(
    private val getSuggestionUseCase: GetSuggestionUseCase,
    private val loanDraftsRepository: LoanDraftsRepository,
    private val profileRepository: LoanApplicationProfileRepository,
    private val formFactory: LoanFormFactory,
    private val validator: LoanFieldValidator,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider()
) : ViewModel() {

    private var currentDraftId: String? = null

    private val _fields = MutableStateFlow<List<FormField>>(emptyList())
    val fields: StateFlow<List<FormField>> = _fields.asStateFlow()

    private val _showPrefillDialog = MutableStateFlow(false)
    val showPrefillDialog: StateFlow<Boolean> = _showPrefillDialog.asStateFlow()

    private val _selectedIncomeCurrency = MutableStateFlow("RUB")
    val selectedIncomeCurrency: StateFlow<String> = _selectedIncomeCurrency.asStateFlow()

    private val _selectedAmountCurrency = MutableStateFlow("RUB")
    val selectedAmountCurrency: StateFlow<String> = _selectedAmountCurrency.asStateFlow()

    private val _currencies = MutableStateFlow(listOf("RUB", "USD", "EUR", "CNY", "USDT"))
    val currencies: StateFlow<List<String>> = _currencies.asStateFlow()

    private val _submissionSuccess = MutableStateFlow(false)
    val submissionSuccess: StateFlow<Boolean> = _submissionSuccess.asStateFlow()

    private val _loanType = MutableStateFlow("")
    
    private val _prefillLoading = MutableStateFlow(false)
    val prefillLoading: StateFlow<Boolean> = _prefillLoading.asStateFlow()

    private val _scrollToField = MutableSharedFlow<String>()
    val scrollToField: SharedFlow<String> = _scrollToField.asSharedFlow()

    private val validationErrors = mutableMapOf<String, String>()
    private val touchedFields = mutableSetOf<String>()
    private val hasAttemptedSubmission = MutableStateFlow(false)

    private val searchQuery = MutableSharedFlow<Pair<String, String>>()

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch(dispatcherProvider.io) {
            searchQuery
                .debounce(500)
                .collect { (id, value) ->
                    val field = _fields.value.find { it.id == id }
                    if (field != null) {
                        val suggestions = getSuggestionUseCase.invoke(value, field.type)
                            .getOrDefault(emptyList())
                        _fields.update { current ->
                            current.map {
                                if (it.id == id) it.copy(suggestions = suggestions.map { s -> s.value })
                                else it
                            }
                        }
                    }
                }
        }
    }

    init {
        observeSearchQuery()
    }

    fun initForm(loanType: String) {
        if (_fields.value.isNotEmpty() && _loanType.value == loanType) return

        _loanType.value = loanType
        validationErrors.clear()
        touchedFields.clear()
        hasAttemptedSubmission.value = false
        _showPrefillDialog.value = true
        
        val formFields = formFactory.createForm(loanType)
        _fields.value = formFields
    }
    
    fun onPrefillDialogResult(confirmed: Boolean) {
        _showPrefillDialog.value = false
        if (confirmed) {
            prefillFromProfile()
        }
    }
    
    private fun prefillFromProfile() {
        viewModelScope.launch(dispatcherProvider.io) {
            _prefillLoading.value = true
            try {
                profileRepository.getProfile()
                    .onSuccess { profile ->
                        val dateFormatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                        val uiFormatter = java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault())
                        
                        fun formatDate(date: String?): String {
                            if (date.isNullOrBlank()) return ""
                             return try {
                                val d = dateFormatter.parse(date)
                                uiFormatter.format(d!!)
                            } catch (_: Exception) {
                                date
                            }
                        }

                        val fio = listOfNotNull(profile.lastName, profile.firstName, profile.patronymic)
                            .filter { it.isNotBlank() }
                            .joinToString(" ")

                        _fields.update { currentFields ->
                            currentFields.map { field ->
                                when(field.id) {
                                    LoanField.FIO.key -> field.copy(value = fio)
                                    LoanField.EMAIL.key -> field.copy(value = profile.email)
                                    LoanField.PHONE.key -> field.copy(value = profile.phone)
                                    LoanField.PASSPORT.key -> field.copy(value = profile.passportSeriesNumber)
                                    LoanField.PASSPORT_ISSUED.key -> field.copy(value = profile.passportIssuedBy)
                                    LoanField.PASSPORT_DATE.key -> field.copy(value = formatDate(profile.passportIssueDate))
                                    LoanField.PASSPORT_CODE.key -> field.copy(value = profile.passportDivisionCode)
                                    LoanField.INN.key -> field.copy(value = profile.inn)
                                    LoanField.SNILS.key -> field.copy(value = profile.snils)
                                    LoanField.ADDRESS.key -> field.copy(value = profile.addressRegistration)
                                    LoanField.ZIP.key -> field.copy(value = profile.postalCode)
                                    LoanField.ORG_NAME.key -> field.copy(value = profile.employerName)
                                    LoanField.ORG_INN.key -> field.copy(value = profile.employerInn)
                                    LoanField.INCOME.key -> field.copy(value = profile.monthlyIncome?.toString() ?: "")
                                    else -> field
                                }
                            }
                        }
                    }
                    .onFailure {
                    }
            } finally {
                _prefillLoading.value = false
            }
        }
    }

    fun onFieldChange(id: String, value: String) {
        val filteredValue = if (id == LoanField.AMOUNT.key || 
            id == LoanField.INCOME.key || 
            id == LoanField.TERM.key || 
            id == LoanField.ADVANCE.key ||
            id == LoanField.INN.key ||
            id == LoanField.ORG_INN.key ||
            id == LoanField.ZIP.key) {
            value.filter { it.isDigit() }
        } else {
            value
        }

        touchedFields.add(id)
        _fields.update { currentFields ->
            currentFields.map { field ->
                if (field.id == id) {
                    field.copy(value = filteredValue)
                } else {
                    field
                }
            }
        }
        updateFieldsErrorState()

        val field = _fields.value.find { it.id == id } ?: return
        if (field.type == FieldType.SUGGEST_FIO ||
            field.type == FieldType.SUGGEST_ADDRESS ||
            field.type == FieldType.SUGGEST_ORG) {
            viewModelScope.launch(dispatcherProvider.io) {
                searchQuery.emit(id to filteredValue)
            }
        }
    }

    fun onIncomeCurrencyChange(currency: String) {
        _selectedIncomeCurrency.value = currency
    }

    fun onAmountCurrencyChange(currency: String) {
        _selectedAmountCurrency.value = currency
    }

    fun submit() {
        hasAttemptedSubmission.value = true
        val currentFields = _fields.value
        currentFields.filter { it.isRequired && it.value.isBlank() }.forEach {
            validationErrors[it.id] = "Обязательное поле"
        }

        updateFieldsErrorState()

        val hasErrors = validationErrors.isNotEmpty()

        if (hasErrors) {
            val firstErrorField = currentFields.firstOrNull { validationErrors.containsKey(it.id) }
            if (firstErrorField != null) {
                viewModelScope.launch(dispatcherProvider.io) {
                    _scrollToField.emit(firstErrorField.id)
                }
            }
            return
        }

        viewModelScope.launch(dispatcherProvider.io) {
            currentDraftId?.let { loanDraftsRepository.deleteDraft(it) }
            currentDraftId = null
            _submissionSuccess.value = true
        }
    }

    fun resetSubmission() {
        _submissionSuccess.value = false
        hasAttemptedSubmission.value = false
        touchedFields.clear()
        updateFieldsErrorState()
    }

    fun saveDraft() {
        val currentFields = _fields.value
        val draftId = currentDraftId ?: UUID.randomUUID().toString()
        val app = LoanApplication(
            id = draftId,
            type = _loanType.value,
            date = LocalDate.now().toString(),
            status = "DRAFT",
            fields = currentFields,
            incomeCurrency = _selectedIncomeCurrency.value,
            amountCurrency = _selectedAmountCurrency.value
        )
        viewModelScope.launch(dispatcherProvider.io) {
            loanDraftsRepository.addDraft(app)
            currentDraftId = draftId
        }
    }

    fun onValidationResult(id: String, isValid: Boolean, error: String?) {
        if (isValid) {
            validationErrors.remove(id)
        } else {
            validationErrors[id] = error ?: "Ошибка валидации"
        }
        updateFieldsErrorState()
    }
    
    fun onSuggestionClick(id: String, value: String) {
        touchedFields.add(id)
        _fields.update { currentFields ->
            currentFields.map { field ->
                if (field.id == id) {
                    field.copy(value = value, suggestions = emptyList())
                } else {
                    field
                }
            }
        }
        updateFieldsErrorState()
    }

    fun onFieldFocusLost(id: String) {
        _fields.update { currentFields ->
            currentFields.map { field ->
                if (field.id == id) {
                    field.copy(suggestions = emptyList())
                } else {
                    field
                }
            }
        }
    }
    
    private fun updateFieldsErrorState() {
        _fields.update { currentFields ->
            currentFields.map { field ->
                val error = validationErrors[field.id]
                val shouldShow = touchedFields.contains(field.id) || hasAttemptedSubmission.value
                field.copy(error = if (shouldShow) error else null)
            }
        }
    }

    fun getValidation(field: FormField): LoanHubUiTextFieldValidation? {
        return validator.getValidation(field)
    }

    fun loadDraft(draftId: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            val draft = loanDraftsRepository.getDraft(draftId) ?: return@launch
            
            currentDraftId = draftId
            _loanType.value = draft.type
            _selectedIncomeCurrency.value = draft.incomeCurrency
            _selectedAmountCurrency.value = draft.amountCurrency
            _fields.value = draft.fields
        }
    }

    fun loadFromImport(loanType: String, fieldsJson: String) {
        _loanType.value = loanType
        validationErrors.clear()
        touchedFields.clear()
        hasAttemptedSubmission.value = false

        val formFields = formFactory.createForm(loanType)
        try {
            val importedFields = Json.decodeFromString<List<FormField>>(fieldsJson)
            _fields.value = formFields.map { formField ->
                val imported = importedFields.find { it.id == formField.id }
                if (imported != null) formField.copy(value = imported.value)
                else formField
            }
        } catch (_: Exception) {
            _fields.value = formFields
        }
    }
}
