package com.android.roomdbtest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.android.roomdbtest.data.repository.NoteRepositoryImpl
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository
import com.android.roomdbtest.presentation.ui.theme.theme.RoomDbTestTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val noteViewModel = viewModels<NotesViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            RoomDbTestTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = col1)

                ) {
                    //NavGraph(noteViewModel = noteViewModel.value)
                    HelloWorld(text = "Hello World")
                    NotesScreen(viewModel = noteViewModel.value)
                }
            }
        }
    }
}
val col1 = Color(0xFFB2A4FF)


@Composable
fun HelloWorld(text : String) {
    Column(modifier= Modifier
        .fillMaxSize()
        .statusBarsPadding()) {
        Text(text = text, color = Color.White)
    }
}

@Composable
fun HomeScreen() {

}

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel(){
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    fun getNote() {
        viewModelScope.launch {
            _notes.value = repository.getNotes()
        }
    }
}

@Composable
fun NotesScreen(viewModel: NotesViewModel ) {
    val notes by viewModel.notes.collectAsState()

    LaunchedEffect(Unit) { // You can replace Unit with a key to control recomposition
        viewModel.getNote()
    }
    val coroutineScope = rememberCoroutineScope()

    Button(onClick = {
        coroutineScope.launch {
            viewModel.getNote()
        }
    }) {
        Text("Load Notes")
    }
}