package com.android.roomdbtest.presentation.update_note

data class UpdateNoteState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)