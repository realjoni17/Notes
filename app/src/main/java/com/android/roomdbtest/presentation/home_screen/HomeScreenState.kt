package com.android.roomdbtest.presentation.home_screen

import com.android.roomdbtest.domain.model.Note

data class HomeScreenState (
    val notes: List<Note> = emptyList()
        )