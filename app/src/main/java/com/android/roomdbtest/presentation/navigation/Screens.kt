package com.android.roomdbtest.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class AppScreens(val route: String) {
    object Auth : AppScreens("auth")
    object Notes : AppScreens("notes")
    object NoteEdit : AppScreens("note_edit") {
        val arguments = listOf(
            navArgument("noteId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )

        fun createRoute(noteId: Long?) = "$route?noteId=${noteId ?: ""}"
    }
}