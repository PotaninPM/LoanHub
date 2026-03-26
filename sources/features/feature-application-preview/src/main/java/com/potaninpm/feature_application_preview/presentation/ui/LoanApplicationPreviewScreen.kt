package com.potaninpm.feature_application_preview.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.database.models.FormField
import com.potaninpm.feature_application_preview.R
import com.potaninpm.feature_application_preview.di.ApplicationPreviewComponentHolder
import com.potaninpm.feature_application_preview.presentation.ui.components.SuccessOverlay
import com.potaninpm.feature_application_preview.presentation.viewmodel.LoanApplicationPreviewViewModel
import com.potaninpm.uikit.presentation.buttons.LoanHubUiButton
import com.potaninpm.uikit.presentation.details.LoanHubUiDetailsTable
import com.potaninpm.uikit.presentation.text.LoanHubUiText
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanApplicationPreviewScreen(
    fieldsJson: String,
    incomeCurrency: String,
    amountCurrency: String,
    loanType: String,
    onBack: () -> Unit,
    onConfirmAndGoHome: () -> Unit
) {
    val fields = try {
        Json.decodeFromString<List<FormField>>(fieldsJson)
    } catch (e: Exception) {
        emptyList()
    }

    val component = ApplicationPreviewComponentHolder.get()
    val viewModel: LoanApplicationPreviewViewModel = viewModel(
        factory = component.provideViewModelFactory(
            initialFields = fields,
            incomeCurrency = incomeCurrency,
            amountCurrency = amountCurrency,
            loanType = loanType
        )
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showSuccess by remember { mutableStateOf(false) }

    if (showSuccess) {
        SuccessOverlay(onGoHome = onConfirmAndGoHome)
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { LoanHubUiText(style = MaterialTheme.typography.titleMedium, text = stringResource(R.string.preview_text)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_text)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    ) {
                        LoanHubUiText(
                            text = stringResource(R.string.check_data),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                item {
                    LoanHubUiDetailsTable(
                        items = state.detailItems
                    )
                }
                
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LoanHubUiButton(
                    text = stringResource(R.string.back),
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = onBack
                )
                LoanHubUiButton(
                    text = stringResource(R.string.confirm),
                    modifier = Modifier.weight(1f),
                    isLoading = state.isLoading,
                    onClick = { 
                        viewModel.submit {
                            showSuccess = true
                        }
                    }
                )
            }
        }
    }
}
