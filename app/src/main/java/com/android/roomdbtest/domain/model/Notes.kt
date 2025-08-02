package com.android.roomdbtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    //val timestamp: Long,

)

class InvalidNoteException(message: String) : Exception(message)