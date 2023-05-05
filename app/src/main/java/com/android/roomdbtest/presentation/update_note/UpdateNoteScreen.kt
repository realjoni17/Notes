package com.android.roomdbtest.presentation.update_note

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.roomdbtest.domain.model.Note

@Composable
fun UpdateNoteScreen(onSaveNote: (Note) -> Unit) {
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Note") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(text = "Note Title") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(text = "Note Content") }
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onSaveNote.invoke(Note(title = title, content = content, )) },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(text = "Save")
                }
            }
        }
    )
}



