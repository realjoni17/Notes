package com.android.roomdbtest.presentation.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("note_list_screen")
    object UpdateNoteScreen : Screens("note_create_update_screen")

    object SplashScreen : Screens("splash_screen")
}