package com.android.roomdbtest.domain.repository

import com.android.roomdbtest.data.local.NoteDao
import com.android.roomdbtest.data.local.NoteDatabase
import com.android.roomdbtest.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(noteId: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}