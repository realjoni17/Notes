package com.android.roomdbtest.domain.repository

import com.android.roomdbtest.data.local.NoteDao
import com.android.roomdbtest.data.local.NoteDatabase
import com.android.roomdbtest.domain.model.Note
import com.google.api.services.drive.Drive
import kotlinx.coroutines.flow.Flow
import kotlin.Result


interface NoteRepository {
    fun getAllNotes(userId: String): Flow<List<Note>>
    fun searchNotes(userId: String, query: String): Flow<List<Note>>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun deleteAllNotes(userId: String)
    suspend fun syncNotesFromFirebase(userId: String)
    suspend fun exportNotesToDrive(userId: String, driveService: Drive): Result<Unit>
    suspend fun importNotesFromDrive(userId: String, driveService: Drive): Result<Unit>
}