package com.android.roomdbtest.data.local

import androidx.room.*
import com.android.roomdbtest.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE userId = :userId")
    fun getAllNotes(userId: String): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE userId = :userId AND (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%') ORDER BY timestamp DESC")
    fun searchNotes(userId: String, query: String): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note WHERE userId = :userId")
    suspend fun deleteAllNotes(userId: String)
}