@file:OptIn(ExperimentalMaterial3Api::class)

package com.jauni.todoapp.presentation
import android.nfc.Tag
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.log

@Composable
fun HomeScreen() {
       LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(3) {
                Text(text = "test")
            }
           item{
               Box(modifier = Modifier.fillMaxWidth()){
                   Button(modifier = Modifier.align(Alignment.Center),
                       onClick = { /*TODO*/ }) {
                       Text(text = "Add Note")
                   }
               }
           }
        }
    }

@Preview
@Composable
fun PreviewHomeScreen(){
   HomeScreen()
}




