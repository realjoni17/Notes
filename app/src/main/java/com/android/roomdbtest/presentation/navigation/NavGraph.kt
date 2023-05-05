package com.android.roomdbtest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.android.roomdbtest.domain.model.Note
import com.android.roomdbtest.presentation.home_screen.HomeScreen
import com.android.roomdbtest.presentation.update_note.UpdateNoteScreen
import com.android.roomdbtest.presentation.update_note.UpdateNoteViewModel

@Composable
fun NavGraph(navController: NavHostController = rememberNavController(),
             noteViewModel: UpdateNoteViewModel)
{
NavHost(navController = navController, startDestination = Screens.HomeScreen.route ){
   composable(route = Screens.HomeScreen.route){
       HomeScreen( navController =navController, noteViewModel = noteViewModel )
   }
   composable(route = Screens.UpdateNoteScreen.route){
      UpdateNoteScreen(noteViewModel = noteViewModel, navController =navController )
   }
}
}