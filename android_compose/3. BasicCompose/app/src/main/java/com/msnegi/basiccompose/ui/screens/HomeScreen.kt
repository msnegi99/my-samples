package com.msnegi.basiccompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun HomeScreen()
{
    // State variable to manage the button click count
    var clickCount by remember { mutableStateOf(0) }

    // Use a Column to arrange elements vertically on the screen
    Column(
        modifier = Modifier
            .fillMaxSize() // Make the Column fill the entire screen
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Center content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
    ) {
        // Text composable
        Text(text = "You clicked the button $clickCount times!")

        // Add some vertical space
        Spacer(modifier = Modifier.height(16.dp))

        // Button composable
        Button(onClick = {
            // Update the state when clicked, which recomposes the Text
            clickCount++
        }) {
            Text("Click Me")
        }
    }
}
