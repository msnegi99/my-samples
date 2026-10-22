package com.msnegi.mycomposesamples

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.msnegi.mycomposesamples.ui.components.CustomButtonWithModifier

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun HomeScreen(navController: NavController)
{
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                    actionIconContentColor = MaterialTheme.colorScheme.secondary
                ),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, "menu")
                    }
                },
                title = {
                    Text("Small Top App Bar")
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color.LightGray.copy(alpha = 0.2f),
                contentColor = Color.Blue
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(30.dp)
                        )
                    }

                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
    ) { innerPadding ->

        var shouldHandleBack by remember { mutableStateOf(true) }
        var showDialog by remember { mutableStateOf(false) }
        BackHandler(enabled = shouldHandleBack) {
            showDialog = true
        }
        val context = LocalContext.current

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = "Confirm Action")
                },
                text = {
                    Text(text = "Are you sure you want to proceed with this action?")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            val activity = (context as? Activity)
                            activity?.let { ActivityCompat.finishAffinity(it) }
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Dismiss")
                    }
                }
            )
        }

        // Use a Column to arrange elements vertically on the screen
        DashboardPreview(innerPadding, navController = navController)
    }
}

@Composable
fun DashboardPreview(innerPadding: PaddingValues, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "Buttons Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("ButtonsScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "Cards Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("CardsScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "Dialogs Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("DialogsScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "Text Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("TextScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "InputText Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("InputTextScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "Checkbox Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("CheckboxScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "RadioGroup Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("RadioGroupScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "Dropdown Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("DropdownScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "DatePicker Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("DatePickerScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "TimePicker Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("TimePickerScreen") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButtonWithModifier(
            text = "Image Screen",
            modifier = Modifier.fillMaxWidth().height(50.dp),
            onClick = { navController.navigate("ImageScreen") }
        )

    }
}
