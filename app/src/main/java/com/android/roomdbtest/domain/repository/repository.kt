package com.android.roomdbtest.domain.repository

import com.android.roomdbtest.data.local.NoteDao
import com.android.roomdbtest.data.local.NoteDatabase
import com.android.roomdbtest.domain.model.Note
import kotlinx.coroutines.flow.Flow


interface NoteRepository {
    fun getAllNotes(userId: String): Flow<List<Note>>
    fun searchNotes(userId: String, query: String): Flow<List<Note>>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun deleteAllNotes(userId: String)
    suspend fun syncNotesFromFirebase(userId: String)
}