package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class LazyColumn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScrollableExample()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScrollableExample() {
    var text by remember { mutableStateOf(TextFieldValue()) }

    // Generate a list of items to display in the LazyColumn
    val items = (1..50).map { "Itemx $it" }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
        // Column 1
        Column(modifier = Modifier.height(300.dp)) {
            // Column 1 content
            Box() {
                LazyRow(
                    modifier = Modifier
                        .background(Color.Gray)
                        .height(100.dp)
                ) {
                    items(items) { item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.White)
                        )
                    }
                }
            }
        }

        // Column 2
        Column(modifier = Modifier.height(300.dp)) {
            // Column 2 content
            Box() {
                LazyColumn(
                    modifier = Modifier
                        .background(Color.Gray)
                        .height(100.dp)
                ) {
                    items(items) { item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.White)
                        )
                    }
                }
            }
        }

        // Column 3
        Column(modifier = Modifier.height(300.dp)) {
            // Column 3 content
            Box() {
                LazyColumn(
                    modifier = Modifier
                        .background(Color.Gray)
                        .height(100.dp)
                ) {
                    items(items) { item ->
                        Text(
                            text = item,
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.White)
                        )
                    }
                }
            }

        }
    }
}
