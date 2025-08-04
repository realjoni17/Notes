package com.android.roomdbtest.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.android.roomdbtest.auth.AuthService
import com.android.roomdbtest.auth.FirebaseAuthService
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.presentation.auth.AuthScreen
import com.android.roomdbtest.presentation.auth.AuthViewModel

import com.android.roomdbtest.presentation.home_screen.NotesScreen
import com.android.roomdbtest.presentation.splash_screen.Splash

import com.android.roomdbtest.presentation.update_note.NoteEditScreen


@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    authService: AuthService = hiltViewModel<AuthViewModel>().authService
) {
    var isCheckingAuth by remember { mutableStateOf(true) }
    var isSignedIn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isSignedIn = authService.isUserSignedIn()
        isCheckingAuth = false
    }
    if (isCheckingAuth) {
        CircularProgressIndicator()
    } else {


        NavHost(
            navController = navController,
            startDestination = if (isSignedIn) AppScreens.Notes.route else AppScreens.Auth.route
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
                val noteId = backStackEntry.arguments?.getString("noteId")?.toString()
                NoteEditScreen(
                    noteId = noteId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}


