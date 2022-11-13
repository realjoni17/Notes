package com.jauni.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteEntity(
@PrimaryKey(autoGenerate = true) var roomId: Long? = null,
val text: String,
)