package com.android.roomdbtest.presentation.home_screen

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.roomdbtest.domain.model.Note

@Composable
fun HomeScreen(notes: List<Note>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(notes) { note ->
            NoteListItem(note = note)
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                startIndent = 16.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun NoteListItem(note: Note) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = note.content,
            style = MaterialTheme.typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

    val notes = listOf(
        Note(
            title = "Note 1",
            content = "This is the content of note 1"

        ),
        Note(
            title = "Note 2",
            content = "This is the content of note 2"

        ),
        Note(
            title = "Note 3",
            content = "This is the content of note 3"

        )
    )

    HomeScreen(notes = notes)
}