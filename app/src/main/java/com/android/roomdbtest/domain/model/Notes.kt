package com.android.roomdbtest.domain.model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

import java.util.Base64.Decoder
import kotlinx.serialization.Serializable



@SuppressLint("UnsafeOptInUsageError")
@Entity(tableName = "note")
@Serializable
data class Note(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0, // for Room
    val noteId: String = UUID.randomUUID().toString(),     // for Firestore
    val userId: String,                                     // Firebase UID
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)


