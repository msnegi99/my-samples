package com.msnegi.mycomposesamples.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.msnegi.mycomposesamples.R
import com.msnegi.mycomposesamples.ui.components.AllButtons
import com.msnegi.mycomposesamples.ui.components.CustomToggleButton
import com.msnegi.mycomposesamples.ui.components.PrimaryButton
import com.msnegi.mycomposesamples.ui.components.PrimaryIconButton
import com.msnegi.mycomposesamples.ui.components.PrimarySquareIconButton
import com.msnegi.mycomposesamples.ui.components.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(
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
                title = { Text("Buttons Screen") },
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
                .verticalScroll(rememberScrollState()),
        ){
            Column(modifier = Modifier.fillMaxSize().padding(all=10.dp)){
                PrimaryButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    enabled = true,) {
                    Text(text = "Enabled")
                }

                var isChecked: Boolean by remember { mutableStateOf(false) }

                CustomToggleButton(
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = it },
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                PrimaryIconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }

                Spacer(modifier = Modifier.height(16.dp))

                PrimarySquareIconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }

                Spacer(modifier = Modifier.height(16.dp))

                SecondaryButton(onClick = {}) {
                    Text(text = "Enabled")
                }

                AllButtons()
            }
        }
    }
}




