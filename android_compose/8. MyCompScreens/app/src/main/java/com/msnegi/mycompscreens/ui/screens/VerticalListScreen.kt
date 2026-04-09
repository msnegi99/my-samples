package com.msnegi.mycompscreens.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.msnegi.mycompscreens.ui.screens.data.DemoDataProvider
import com.msnegi.mycompscreens.ui.screens.data.model.Item
import com.msnegi.mycompscreens.ui.theme.MyCompScreensTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerticalListScreen(
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
                title = { Text("VerticalList Sample") },
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
            VerticalListExample(LocalContext.current)
        }
    }
}

@Composable
fun VerticalListExample(context: Context) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalListView()
    }
}

@Composable
fun VerticalListView() {
    val list = remember { DemoDataProvider.itemList }
    LazyColumn {
        items(
            items = list,
            itemContent = { item ->
                if ((item.id % 3) == 0) {
                    VerticalListItemSmall(item = item)
                } else {
                    VerticalListItem(item = item)
                }
                ListItemDivider()
            }
        )
    }
}


@Composable
fun VerticalListItem(item: Item, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    Column(
        modifier =
            modifier.fillMaxWidth().padding(16.dp).testTag("${"home_screen_list_item"}-${item.id}")
    ) {
        val imageModifier =
            Modifier.height(150.dp)
                .fillMaxWidth()
                .clip(shape = androidx.compose.material.MaterialTheme.shapes.medium)

        Image(
            painter = painterResource(item.imageId),
            modifier = imageModifier,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))
        Text(text = item.title, style = typography.titleLarge)
        Text(text = item.subtitle, style = typography.bodyMedium)

        Text(text = item.source, style = typography.titleSmall)
    }
}

@Composable
private fun ListItemDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
fun VerticalListItemSmall(item: Item, modifier: Modifier = Modifier) {
    val typography = MaterialTheme.typography
    Row(modifier = Modifier.clickable(onClick = {}).padding(16.dp)) {
        ItemImage(item, Modifier.padding(end = 16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, style = typography.titleMedium)
            Text(item.subtitle, style = typography.bodyMedium)
        }
        FavIcon(modifier)
    }
}

@Composable
fun ItemImage(item: Item, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = item.imageId),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier =
            modifier.size(100.dp, 80.dp).clip(androidx.compose.material.MaterialTheme.shapes.medium)
    )
}

@Composable
fun FavIcon(modifier: Modifier = Modifier) {
    val isFavourite = remember { mutableStateOf(true) }
    IconToggleButton(
        checked = isFavourite.value,
        onCheckedChange = { isFavourite.value = !isFavourite.value }
    ) {
        if (isFavourite.value) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = null, modifier = modifier)
        } else {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    MyCompScreensTheme {
        VerticalListView()
    }
}
