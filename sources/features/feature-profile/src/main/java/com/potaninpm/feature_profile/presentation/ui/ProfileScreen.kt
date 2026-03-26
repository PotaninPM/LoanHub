package com.potaninpm.feature_profile.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.potaninpm.feature_profile.R
import com.potaninpm.feature_profile.di.ProfileComponentHolder
import com.potaninpm.feature_profile.presentation.components.LogoutButton
import com.potaninpm.feature_profile.presentation.components.ProfileField
import com.potaninpm.feature_profile.presentation.components.ProfileHeader
import com.potaninpm.feature_profile.presentation.components.ProfileInfoCard
import com.potaninpm.feature_profile.presentation.components.ProfileSkeleton
import com.potaninpm.feature_profile.presentation.viewmodel.ProfileViewModel
import com.potaninpm.feature_profile.utils.formatFio
import com.potaninpm.uikit.presentation.DefaultErrorView
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onEditClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    refreshTrigger: Long = 0
) {
    val component = ProfileComponentHolder.get()
    val viewModel: ProfileViewModel = viewModel(
        factory = component.provideViewModelFactory()
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(refreshTrigger) {
        if (refreshTrigger > 0) {
            viewModel.loadProfile()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { LoanHubUiText(style = MaterialTheme.typography.titleMedium, text = stringResource(R.string.profile_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.profile_edit_content_desc)
                        )
                    }
                }
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            if (state.isLoading) {
                 ProfileSkeleton(modifier = Modifier.fillMaxSize())
            } else if (state.error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    DefaultErrorView(onRetry = { viewModel.loadProfile() })
                }
            } else {
                val profile = state.profile
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ProfileHeader(profile)
                    }

                    item {
                        SectionTitle(stringResource(R.string.profile_section_personal))
                    }
                    item {
                        ProfileInfoCard {
                            ProfileField(
                                icon = Icons.Default.Person,
                                label = stringResource(R.string.profile_label_fio),
                                value = formatFio(profile, full = true)
                            )
                            ProfileField(
                                icon = Icons.Default.CalendarMonth,
                                label = stringResource(R.string.profile_label_birth_date),
                                value = profile.birthDate,
                                showDivider = false
                            )
                        }
                    }

                    item {
                        SectionTitle(stringResource(R.string.profile_section_passport))
                    }
                    item {
                        ProfileInfoCard {
                            ProfileField(
                                icon = Icons.Default.Badge,
                                label = stringResource(R.string.profile_label_passport_series_number),
                                value = profile.passportSeriesNumber
                            )
                            ProfileField(
                                icon = Icons.Default.Badge,
                                label = stringResource(R.string.profile_label_passport_issued_by),
                                value = profile.passportIssuedBy
                            )
                            ProfileField(
                                icon = Icons.Default.CalendarMonth,
                                label = stringResource(R.string.profile_label_passport_issue_date),
                                value = profile.passportIssueDate
                            )
                            ProfileField(
                                icon = Icons.Default.Description,
                                label = stringResource(R.string.profile_label_passport_division_code),
                                value = profile.passportDivisionCode,
                                showDivider = false
                            )
                        }
                    }

                    item {
                        SectionTitle(stringResource(R.string.profile_section_documents))
                    }
                    item {
                        ProfileInfoCard {
                            ProfileField(
                                icon = Icons.Default.Description,
                                label = stringResource(R.string.profile_label_inn),
                                value = profile.inn
                            )
                            ProfileField(
                                icon = Icons.Default.Description,
                                label = stringResource(R.string.profile_label_snils),
                                value = profile.snils,
                                showDivider = false
                            )
                        }
                    }

                    item {
                        SectionTitle(stringResource(R.string.profile_section_contact))
                    }
                    item {
                        ProfileInfoCard {
                            ProfileField(
                                icon = Icons.Default.Phone,
                                label = stringResource(R.string.profile_label_phone),
                                value = profile.phone
                            )
                            ProfileField(
                                icon = Icons.Default.Email,
                                label = stringResource(R.string.profile_label_email),
                                value = profile.email
                            )
                            ProfileField(
                                icon = Icons.Default.LocationOn,
                                label = stringResource(R.string.profile_label_address_registration),
                                value = profile.addressRegistration
                            )
                            ProfileField(
                                icon = Icons.Default.LocationOn,
                                label = stringResource(R.string.profile_label_postal_code),
                                value = profile.postalCode,
                                showDivider = false
                            )
                        }
                    }

                    item {
                        SectionTitle(stringResource(R.string.profile_section_work))
                    }
                    item {
                        ProfileInfoCard {
                            ProfileField(
                                icon = Icons.Default.Work,
                                label = stringResource(R.string.profile_label_employer_name),
                                value = profile.employerName
                            )
                            ProfileField(
                                icon = Icons.Default.Description,
                                label = stringResource(R.string.profile_label_employer_inn),
                                value = profile.employerInn
                            )
                            ProfileField(
                                icon = Icons.Default.Badge,
                                label = stringResource(R.string.profile_label_job_title),
                                value = profile.jobTitle
                            )
                            ProfileField(
                                icon = Icons.Default.CalendarMonth,
                                label = stringResource(R.string.profile_label_work_experience),
                                value = profile.workExperience
                            )
                            ProfileField(
                                icon = Icons.Default.Description,
                                label = stringResource(R.string.profile_label_monthly_income),
                                value = profile.monthlyIncome?.toString(),
                                showDivider = false
                            )
                        }
                    }

                    item {
                        LogoutButton(
                            onClick = {
                                viewModel.logout()
                                onLogout()
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
}

@Composable
internal fun SectionTitle(title: String) {
    LoanHubUiText(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
