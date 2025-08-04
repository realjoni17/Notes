package com.android.roomdbtest.data.repository

import android.util.Log
import com.android.roomdbtest.data.local.NoteDao
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository
import com.google.api.client.http.FileContent
import kotlinx.serialization.json.Json
import com.google.api.services.drive.Drive

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class NoteRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val noteDao: NoteDao
) : NoteRepository {

    override fun getAllNotes(userId: String): Flow<List<Note>> {
        return noteDao.getAllNotes(userId)
    }

    override fun searchNotes(userId: String, query: String): Flow<List<Note>> {
        return noteDao.searchNotes(userId, query)
    }

    override suspend fun insertNote(note: Note): Long {
        val id = noteDao.insertNote(note)

        firestore.collection("users")
            .document(note.userId)
            .collection("notes")
            .document(note.noteId)
            .set(note)
            .addOnFailureListener {
                Log.e("NoteRepo", "Failed to insert note in Firestore", it)
            }

        return id
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note)

        firestore.collection("users")
            .document(note.userId)
            .collection("notes")
            .document(note.noteId)
            .set(note)
            .addOnFailureListener {
                Log.e("NoteRepo", "Failed to update note in Firestore", it)
            }
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)

        firestore.collection("users")
            .document(note.userId)
            .collection("notes")
            .document(note.noteId)
            .delete()
            .addOnFailureListener {
                Log.e("NoteRepo", "Failed to delete note in Firestore", it)
            }
    }

    override suspend fun deleteAllNotes(userId: String) {
        noteDao.deleteAllNotes(userId)

        val notesRef = firestore.collection("users").document(userId).collection("notes")
        val snapshot = notesRef.get().await()
        for (doc in snapshot.documents) {
            doc.reference.delete()
        }
    }

    override suspend fun syncNotesFromFirebase(userId: String) {
        try {
            val snapshot = firestore.collection("users")
                .document(userId)
                .collection("notes")
                .get()
                .await()

            val notesFromFirebase = snapshot.toObjects(Note::class.java)

            // Clear local notes for the user (optional if you want a clean sync)
            noteDao.deleteAllNotes(userId)

            // Insert each note
            for (note in notesFromFirebase) {
                noteDao.insertNote(note)
            }

            Log.d("NoteRepo", "Successfully synced ${notesFromFirebase.size} notes from Firestore.")

        } catch (e: Exception) {
            Log.e("NoteRepo", "Error syncing notes from Firestore", e)
        }
    }

    override suspend fun exportNotesToDrive(userId: String, driveService: Drive): Result<Unit> {
        return try {
            val notes: List<Note> = noteDao.getAllNotes(userId).first()
            val notesJson: String = Json.encodeToString(notes)
            val fileMetadata = com.google.api.services.drive.model.File().apply {
                name = "notes_backup_${userId}_${System.currentTimeMillis()}.json"
                parents = listOf("appDataFolder")
            }
            val mediaContent = FileContent("application/json", java.io.File.createTempFile("notes", ".json").apply {
                writeText(notesJson)
            })
            driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("NoteRepo", "Failed to export notes to Drive", e)
            Result.failure(e)
        }
    }

    override suspend fun importNotesFromDrive(userId: String, driveService: Drive): Result<Unit> {
        return try {
            val fileList = driveService.files().list()
                .setSpaces("appDataFolder")
                .setFields("files(id, name)")
                .execute()
            val latestFile = fileList.files.maxByOrNull { it.name }
                ?: return Result.failure(Exception("No backup files found"))
            val fileContent = driveService.files().get(latestFile.id)
                .executeMediaAsInputStream()
                .bufferedReader()
                .use { it.readText() }
            val notes = Json.decodeFromString<List<Note>>(fileContent)
            noteDao.deleteAllNotes(userId)
            notes.forEach { noteDao.insertNote(it.copy(userId = userId)) }
            syncNotesToFirebase(notes, userId)
              kotlin.Result.success(Unit)
        } catch (e: Exception) {
            Log.e("NoteRepo", "Failed to import notes from Drive", e)
            kotlin.Result.failure(e)
        }
    }

    private suspend fun syncNotesToFirebase(notes: List<Note>, userId: String) {
        try {
            val batch = firestore.batch()
            notes.forEach { note ->
                val ref = firestore.collection("users")
                    .document(userId)
                    .collection("notes")
                    .document(note.noteId)
                batch.set(ref, note)
            }
            batch.commit().await()
            Log.d("NoteRepo", "Successfully synced ${notes.size} notes to Firestore.")
        } catch (e: Exception) {
            Log.e("NoteRepo", "Error syncing notes to Firestore", e)
        }
    }


}
