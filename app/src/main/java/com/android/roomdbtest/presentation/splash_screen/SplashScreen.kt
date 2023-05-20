package com.android.roomdbtest.presentation.splash_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.android.roomdbtest.presentation.navigation.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navcontroller:NavController){
    LaunchedEffect(key1 = true){
        delay(2000L)
        navcontroller.navigate(Screens.HomeScreen.route){
        popUpTo(Screens.SplashScreen.route){
            inclusive = true
        }
    }
}
    Splash()
}

