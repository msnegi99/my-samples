package com.msnegi.mycomposesamples.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.msnegi.mycomposesamples.ui.components.CustomToggleButton
import com.msnegi.mycomposesamples.ui.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckboxScreen(
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
                title = { Text("Checkbox Screen") },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.LightGray)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp)) {
                LabelledCheckbox()
                DynamicCheckboxList()
            }
        }
    }
}

@Composable
fun LabelledCheckbox() {
    Row(modifier = Modifier.padding(8.dp),verticalAlignment = Alignment.CenterVertically) {
        val isChecked = remember { mutableStateOf(false) }

        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green)
        )

        Text(text = "Labelled Check Box")

        Log.d("dd","text = ${isChecked}")
    }
}

data class CheckboxItem(val id: Int, val label: String, var isChecked: Boolean)

@Composable
fun DynamicCheckboxList() {
    // Initialize a dynamic list of items
    val items = remember {
        mutableStateListOf(
            CheckboxItem(1, "Option A", false),
            CheckboxItem(2, "Option B", false),
            CheckboxItem(3, "Option C", false)
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        items.forEachIndexed { index, item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().clickable {
                    items[index] = item.copy(isChecked = !item.isChecked)
                }
            ) {
                Checkbox(
                    checked = item.isChecked,
                    onCheckedChange = { isChecked ->
                        // Update the state to trigger recomposition
                        items[index] = item.copy(isChecked = isChecked)
                    }
                )
                Text(text = item.label, modifier = Modifier.padding(start = 8.dp))
            }
        }

        PrimaryButton(
            onClick = {
                items.forEachIndexed { index, item ->
                    Log.d("dd","text = $item.label  ${item.isChecked}")
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = true,) {
            Text(text = "Enabled")
        }

    }
}


