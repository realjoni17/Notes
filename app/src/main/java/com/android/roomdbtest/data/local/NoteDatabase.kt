package com.android.roomdbtest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

import com.android.roomdbtest.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao() :NoteDao

    companion object{
        const val DATABASE_NAME = "notes_db"
    }
}
