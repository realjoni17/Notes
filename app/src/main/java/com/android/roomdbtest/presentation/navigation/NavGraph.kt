package com.android.roomdbtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.presentation.auth.AuthScreen
import com.android.roomdbtest.presentation.home_screen.HomeScreen
import com.android.roomdbtest.presentation.home_screen.NotesScreen
import com.android.roomdbtest.presentation.splash_screen.Splash
import com.android.roomdbtest.presentation.splash_screen.SplashScreen
import com.android.roomdbtest.presentation.update_note.NoteEditScreen



@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Auth.route
    ) {
        composable(AppScreens.Auth.route) {
            AuthScreen(
                onSignInSuccess = {
                    navController.navigate(AppScreens.Notes.route) {
                        popUpTo(AppScreens.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.Notes.route) {
            NotesScreen(
                onNoteClick = { noteId ->
                    navController.navigate(AppScreens.NoteEdit.createRoute(noteId))
                },
                onAddNote = {
                    navController.navigate(AppScreens.NoteEdit.createRoute(null))
                }
            )
        }

        composable(
            route = AppScreens.NoteEdit.route,
            arguments = AppScreens.NoteEdit.arguments
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            NoteEditScreen(
                noteId = noteId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}