package com.android.roomdbtest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.android.roomdbtest.presentation.home_screen.HomeScreen
import com.android.roomdbtest.presentation.navigation.NavGraph
import com.android.roomdbtest.presentation.ui.theme.theme.RoomDbTestTheme
import com.android.roomdbtest.presentation.update_note.UpdateNoteScreen
import com.android.roomdbtest.presentation.update_note.UpdateNoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val noteViewModel = viewModels<UpdateNoteViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDbTestTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = col1)

                ) {
                    NavGraph(noteViewModel = noteViewModel.value)
                }
            }
        }
    }
}
val col1 = Color(0xFFB2A4FF)
