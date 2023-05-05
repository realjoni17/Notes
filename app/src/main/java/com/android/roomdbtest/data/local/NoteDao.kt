package com.android.roomdbtest.data.local

import androidx.room.*
import com.android.roomdbtest.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao{
    @Query ("Select * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("Select * FROM note WHERE id = :id")
    suspend fun getNoteById(id:Int):Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertnote(note: Note)

    @Delete
    suspend fun deleteNote(note:Note)
}