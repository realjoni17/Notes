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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.presentation.col1
import com.android.roomdbtest.presentation.navigation.Screens
import com.android.roomdbtest.presentation.update_note.UpdateNoteViewModel
import kotlin.random.Random


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    notes: List<Note>,
    noteViewModel: UpdateNoteViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes") }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(notes) { note ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            elevation = 8.dp,
                            shape = RoundedCornerShape(16.dp),
                            onClick = {  }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(text = note.title, style = MaterialTheme.typography.h6)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = note.content, style = MaterialTheme.typography.body2)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(
                                        onClick = { noteViewModel.deleteNote(note = note) }
                                    ) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete")
                                    }
                                }
                            }
                        }
                    }
                }
                FloatingActionButton(
                    onClick = { navController.navigate(Screens.UpdateNoteScreen.route)},
                    modifier = Modifier.align(Alignment.End),
                    content = {
                        Icon(Icons.Filled.Add, contentDescription = "Add")
                    },
                    backgroundColor = Color(0xFF576CBC)
                )
            }
        }
    )
}
