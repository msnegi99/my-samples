package com.msnegi.basiccompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import com.msnegi.basiccompose.ui.theme.BasicComposeTheme

class HomeActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicComposeTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyTextView(
                        data1 = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
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
                    floatingActionButton = {
                        FloatingActionButton(onClick = {}) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "")
                        }
                    },
                ) { innerPadding ->
                    //ScrollContent(innerPadding)
                    /*MyTextView(
                        data1 = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    AppNavigation(modifier = Modifier.padding(innerPadding),"home")
                    //HomeScreen(navigateToProfile = {})
                    //AppNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}