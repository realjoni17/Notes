package com.android.roomdbtest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.roomdbtest.presentation.navigation.AppNavigation
import com.android.roomdbtest.presentation.ui.theme.theme.NotesTheme
import com.google.android.gms.common.internal.ClientIdentity

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = col1)

                ) {
                  AppNavigation()
                }
            }
        }
    }
}
val col1 = Color(0xFFB2A4FF)
