package com.android.roomdbtest.presentation.update_note

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.presentation.navigation.Screens



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")

@Composable
fun UpdateNoteScreen(
    noteViewModel: UpdateNoteViewModel,
    navController: NavController
)
{
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add Note") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screens.HomeScreen.route) }) {
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
                    label = { Text(text = "Note Title") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text(text = "Note Content") },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)

                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        if (title.isNotEmpty() && content.isNotEmpty()) {
                            noteViewModel.addNote(note = Note(title = title, content = content))
                            Toast.makeText(context,"Note Added",Toast.LENGTH_SHORT).show()
                        }
                        else{

                            Toast.makeText(context,"Please enter note",Toast.LENGTH_SHORT).show()
                        }
                       navController.navigate(Screens.HomeScreen.route)
                              },
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF576CBC))
                ) {
                    Text(text = "Save")
                }
            }
        }
    )
}



