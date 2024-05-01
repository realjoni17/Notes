package com.android.roomdbtest.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.android.roomdbtest.common.SafeApiRequest
import com.android.roomdbtest.data.local.NoteDao
import com.android.roomdbtest.data.network.ApiService
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.domain.repository.NoteRepository
import com.android.roomdbtest.mappers.toDomain

import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao, private val apiService: ApiService) : NoteRepository,
    SafeApiRequest() {

    override suspend fun getNotes(): List<Note> {
        val response = safeApiRequest { apiService.getNotes() }
        Log.d(TAG, "Vanni: $response")
        return response?.toDomain() ?: emptyList()
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
