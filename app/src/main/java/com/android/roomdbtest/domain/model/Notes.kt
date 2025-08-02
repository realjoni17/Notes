package com.android.roomdbtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Date

)

