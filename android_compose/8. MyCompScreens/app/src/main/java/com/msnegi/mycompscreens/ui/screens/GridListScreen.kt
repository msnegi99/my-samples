package com.msnegi.mycompscreens.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.msnegi.mycompscreens.ui.screens.data.DemoDataProvider
import com.msnegi.mycompscreens.ui.theme.MyCompScreensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridListScreen(
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
                title = { Text("Grid List Sample") },
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
                .background(Color.White)
        ){
            GridListExample(LocalContext.current)
        }
    }
}

@Composable
fun GridListExample(context: Context) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GridListView()
    }
}

@Composable
fun GridListView() {
    // TODO: NO IN-BUILT GRID VIEW NOT AVAILABLE YET USING ROWS FOR NOW
    // GRIDS are not lazy driven yet so let's wait for Lazy Layout to make grids
    val list = remember { DemoDataProvider.itemList.take(4) }
    val posts = remember { DemoDataProvider.tweetList }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        VerticalGrid(columns = 2) { list.forEach { GridListItem(item = it) } }
        VerticalGrid(columns = 4) {
            posts.forEach {
                StoryItem(
                    profileImageId = it.authorImageId,
                    profileName = it.author,
                    isMe = it.id == 1,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    onClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    MyCompScreensTheme {
        GridListView()
    }
}
