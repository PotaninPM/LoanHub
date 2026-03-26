package com.potaninpm.feature_my_requests.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.potaninpm.feature_my_requests.R
import com.potaninpm.feature_my_requests.presentation.state.ExportFormat
import com.potaninpm.uikit.presentation.text.LoanHubUiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRequestsTopBar(
    containerColor: Color,
    onImportJson: () -> Unit,
    onImportCsv: () -> Unit,
    onShowExportDialog: (ExportFormat) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            LoanHubUiText(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(R.string.my_requests_title),
            )
        },
        actions = {
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painter = painterResource(R.drawable.upload_file_24px),
                        contentDescription = stringResource(R.string.menu_desc)
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { LoanHubUiText(stringResource(R.string.menu_import_json)) },
                        onClick = {
                            showMenu = false
                            onImportJson()
                        }
                    )
                    DropdownMenuItem(
                        text = { LoanHubUiText(stringResource(R.string.menu_import_csv)) },
                        onClick = {
                            showMenu = false
                            onImportCsv()
                        }
                    )
                    DropdownMenuItem(
                        text = { LoanHubUiText(stringResource(R.string.menu_export_json)) },
                        onClick = {
                            showMenu = false
                            onShowExportDialog(ExportFormat.JSON)
                        }
                    )
                    DropdownMenuItem(
                        text = { LoanHubUiText(stringResource(R.string.menu_export_csv)) },
                        onClick = {
                            showMenu = false
                            onShowExportDialog(ExportFormat.CSV)
                        }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = containerColor
        )
    )
}
