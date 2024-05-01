/*
package com.android.roomdbtest.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.presentation.navigation.Screens
import com.android.roomdbtest.presentation.update_note.UpdateNoteViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun HomeScreen(noteViewModel: UpdateNoteViewModel,navController: NavController) {
    val notes = noteViewModel.noteList.collectAsState().value
    NoteListScreen(notes = notes, noteViewModel =noteViewModel, navController = navController )
}
*/
