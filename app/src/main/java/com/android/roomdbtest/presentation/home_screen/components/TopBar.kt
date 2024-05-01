package com.android.roomdbtest.presentation.home_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .statusBarsPadding()) {
        TopAppBar(title = { Text(text = "Notes") })
    }
}