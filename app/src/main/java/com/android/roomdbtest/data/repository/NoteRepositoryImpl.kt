package com.android.roomdbtest.data.repository

import com.android.roomdbtest.data.local.NoteDao
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl
   (private val noteDao: NoteDao
    ) : NoteRepository {

        override fun getNotes(): Flow<List<Note>> {
            return noteDao.getNotes()
        }

        override suspend fun getNoteById(noteId: Int): Note? {
            return noteDao.getNoteById(noteId)
        }

        override suspend fun insertNote(note: Note) {
            return noteDao.insertnote(note)
        }

        override suspend fun deleteNote(note: Note) {
            return noteDao.deleteNote(note)
        }


    }
