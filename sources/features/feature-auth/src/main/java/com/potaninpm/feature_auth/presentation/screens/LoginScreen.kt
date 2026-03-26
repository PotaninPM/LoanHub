package com.potaninpm.feature_auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.feature_auth.R
import com.potaninpm.feature_auth.di.AuthComponentHolder
import com.potaninpm.feature_auth.presentation.state.AuthState
import com.potaninpm.feature_auth.presentation.viewModels.AuthViewModel
import com.potaninpm.uikit.presentation.buttons.LoanHubUiButton
import com.potaninpm.uikit.presentation.buttons.LoanHubUiTestAccountsButton
import com.potaninpm.uikit.presentation.inputs.LoanHubUiTextField
import com.potaninpm.uikit.presentation.modals.ChooseAccountDialog
import com.potaninpm.uikit.presentation.modals.TestUser
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@Composable
fun LoginScreen(
    onAuthSuccess: () -> Unit,
) {
    val component = AuthComponentHolder.get()
    val authViewModel: AuthViewModel = viewModel(factory = component.provideViewModelFactory())

    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {
        if (authState is AuthState.Authorized) {
            onAuthSuccess()
        }
    }

    LoginScreenContent(
        authViewModel = authViewModel
    )
}

@Composable
private fun LoginScreenContent(
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val isLoading by authViewModel.isLoading.collectAsStateWithLifecycle()
    val error by authViewModel.error.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .systemBarsPadding()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { focusManager.clearFocus() }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoanHubUiText(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (error != null) {
            LoanHubUiText(
                text = stringResource(R.string.login_error_generic),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        LoanHubUiTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = stringResource(R.string.login_hint_email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LoanHubUiTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.login_hint_password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        LoanHubUiButton(
            text = if (isLoading) stringResource(R.string.login_btn_loading) else stringResource(com.potaninpm.feature_auth.R.string.login_btn_enter),
            onClick = { authViewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            isEnabled = !isLoading && email.isNotBlank() && password.isNotBlank()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LoanHubUiButton(
            text = stringResource(R.string.login_btn_register),
            onClick = { authViewModel.register(email, password) },
            modifier = Modifier.fillMaxWidth(),
            isEnabled = !isLoading && email.isNotBlank() && password.isNotBlank(),
        )
        Spacer(modifier = Modifier.height(32.dp))

        var showTestAccountsDialog by remember { mutableStateOf(false) }

        LoanHubUiTestAccountsButton(
            onClick = { showTestAccountsDialog = true }
        )

        if (showTestAccountsDialog) {
            ChooseAccountDialog(
                title = stringResource(R.string.login_dialog_test_accounts_title),
                users = listOf(
                    TestUser(
                        email = "potaninpm@gmail.com",
                        password = "Mikle21032008",
                        badgeStatus = "Админ",
                        badgeColorStart = 0xFFFF4E50,
                        badgeColorEnd = 0xFFF9D423
                    ),
                    TestUser(
                        email = "user@gmail.com",
                        password = "Password123",
                        badgeStatus = "Пользователь",
                        badgeColorStart = 0xFF00C9FF,
                        badgeColorEnd = 0xFF00C9FF
                    )
                ),
                confirmButtonText = stringResource(R.string.login_dialog_btn_confirm),
                denyButtonText = stringResource(R.string.login_dialog_btn_cancel),
                onConfirm = { testUser ->
                    email = testUser.email
                    password = testUser.password
                    authViewModel.login(email, password)
                    showTestAccountsDialog = false
                },
                onDeny = { showTestAccountsDialog = false }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
