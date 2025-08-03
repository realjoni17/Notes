package com.android.roomdbtest.data.repository

import android.util.Log
import com.android.roomdbtest.data.local.NoteDao
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
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

}
