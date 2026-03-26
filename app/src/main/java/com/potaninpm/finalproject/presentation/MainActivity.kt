package com.potaninpm.finalproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.potaninpm.navigation.RootNavHost

import com.potaninpm.uikit.presentation.theme.LoanHubUiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LoanHubUiTheme {
                RootNavHost()
            }
        }
    }
}