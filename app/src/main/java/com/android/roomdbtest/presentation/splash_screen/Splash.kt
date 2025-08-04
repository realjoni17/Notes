package com.android.roomdbtest.presentation.splash_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.android.roomdbtest.R


@Composable
fun Splash() {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Image(imageVector = ImageVector
            .vectorResource(id = R.drawable.undraw_add_notes_re_ln36),
            contentDescription = "",
            alignment = Alignment.Center
        )
    }
}


