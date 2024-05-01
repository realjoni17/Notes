package com.android.roomdbtest.data.network.dto

data class NotesDtoItem(
    val CreatedAt: String,
    val UserID: Int,
    val body: String,
    val id: Int,
    val title: String
)