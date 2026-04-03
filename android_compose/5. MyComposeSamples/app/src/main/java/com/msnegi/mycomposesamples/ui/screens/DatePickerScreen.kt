package com.msnegi.mycomposesamples.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerScreen(
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
                title = { Text("DatePicker Sample") },
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
            DatePickerExample(LocalContext.current)
        }
    }
}

@Composable
fun DatePickerExample(context: Context) {
    // Initializing a Calendar
    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    // Declaring a string value to store date in string format
    val date = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting initial values as current values (present year, month and day)
    val datePicker = DatePickerDialog(
        context, { _, mYear, mMonth, mDayOfMonth ->
            date.value = "$mDayOfMonth-${mMonth + 1}-$mYear"
        }, year, month, day
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Creating a button that onClick displays the DatePickerDialog
        Button(
            onClick = { datePicker.show() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58))
        ) {
            Text(
                text = "Open Date Picker",
                color = Color.White
            )
        }

        // Adding a space of 100dp height
        Spacer(modifier = Modifier.size(20.dp))

        // Displaying the mDate value in the Text
        Text(
            text = "Selected Date: ${date.value}",
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}


