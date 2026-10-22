package com.msnegi.mycomposesamples.ui.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                    actionIconContentColor = MaterialTheme.colorScheme.secondary
                ),
                title = { Text("TimePicker Sample") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        // Detail screen content goes here
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .background(Color.LightGray)
        ){
            TimePickerContent()
        }
    }
}

@Composable
fun TimePickerContent() {
    // Accessing the current Android context
    val context = LocalContext.current

    // Get the current time using Calendar
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    // Mutable state to store the selected time as a string
    var selectedTime by remember { mutableStateOf("") }

    // Creating a TimePickerDialog in 12-hour format
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            // Convert 24-hour to 12-hour format
            val hour12 = if (selectedHour % 12 == 0) 12 else selectedHour % 12
            val amPm = if (selectedHour < 12) "AM" else "PM"

            // Format time with leading zeros for minutes
            selectedTime = String.format("%d:%02d %s", hour12, selectedMinute, amPm)
        },
        hour,   // Current hour
        minute, // Current minute
        false   // false = 12-hour format
    )

    // Layout for the UI elements
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Padding around the column
        verticalArrangement = Arrangement.Center, // Center elements vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center elements horizontally
    ) {
        // Button to show the Time Picker Dialog
        Button(
            onClick = { timePickerDialog.show() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F9D58)) // Green button
        ) {
            Text(text = "Open Time Picker", color = Color.White)
        }

        // Spacer for vertical spacing
        Spacer(modifier = Modifier.height(50.dp))

        // Display the selected time, or a fallback message
        Text(
            text = if (selectedTime.isNotEmpty()) "Selected Time:\n$selectedTime" else "No time selected",
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}