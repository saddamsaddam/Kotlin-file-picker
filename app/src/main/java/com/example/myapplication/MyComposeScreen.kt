package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MyComposeScreen : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeScreenContent()
        }
    }
}

@Composable
fun MyComposeScreenContent() {
    // Outer composable function
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting(name = "Alice")
            Spacer(modifier = Modifier.height(16.dp))
            Greeting(name = "Bob")
            Spacer(modifier = Modifier.height(16.dp))
            Greeting(name = "Saddamnvn")
        }
    }
}

@Composable
fun Greeting(name: String) {
    // Composable function within the class
    Text(
        text = "Hello, $name!",
        color = Color.Black,
        style = TextStyle(fontSize = 24.sp)
    )
}
