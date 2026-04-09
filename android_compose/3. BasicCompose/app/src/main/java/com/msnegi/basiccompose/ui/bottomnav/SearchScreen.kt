package com.msnegi.basiccompose.ui.bottomnav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SearchScreen(navController: NavController)
{
    Column(
        modifier = Modifier
            .fillMaxSize(), // Make the Column fill the entire screen ,
        verticalArrangement = Arrangement.Center, // Center content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
    ) {
        // Add some vertical space
        Spacer(modifier = Modifier.height(16.dp))

        // Button composable
        Button(onClick = {
        }) {
            Text("Search Screen")
        }
    }

}

