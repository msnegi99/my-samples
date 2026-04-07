package com.msnegi.composebottomnav.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.msnegi.composebottomnav.R
import com.msnegi.composebottomnav.viewmodel.MainViewModel

sealed class Screens(val route: String, val title: String) {

    sealed class FavoriteScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ) : Screens(
        route,
        title
    ) {
        object Favorite : FavoriteScreens("favorite", "Favorite", Icons.Filled.Favorite)
        object NearBy : FavoriteScreens("nearby", "Nearby", Icons.Filled.Notifications)
        object Reserved : FavoriteScreens("reserved", "Reserved", Icons.Filled.Person)
        object Saved : FavoriteScreens("saved", "Saved", Icons.Filled.Person)

    }
}

val screensInHomeFromBottomNav = listOf(
    Screens.FavoriteScreens.Favorite,
    Screens.FavoriteScreens.NearBy,
    Screens.FavoriteScreens.Reserved,
    Screens.FavoriteScreens.Saved
)



