package com.jauni.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jauni.todoapp.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("select * from note")
    fun getAll():Flow<List<NoteEntity>>

    @Insert
    fun insert(note:NoteEntity)

    @Update
    fun update(note:NoteEntity)

    @Delete
    fun delete(note:NoteEntity)
}