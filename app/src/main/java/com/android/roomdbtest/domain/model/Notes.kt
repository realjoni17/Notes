package com.android.roomdbtest.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
)
{
    companion object {

        val noteColors = listOf("")
    }
}
class InvalidNoteException(message: String) : Exception(message)