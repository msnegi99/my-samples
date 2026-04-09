package com.msnegi.mycompscreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msnegi.mycompscreens.ui.screens.GridListScreen
import com.msnegi.mycompscreens.ui.screens.HorizontalListScreen
import com.msnegi.mycompscreens.ui.screens.PhotoPickerScreen
import com.msnegi.mycompscreens.ui.screens.VerticalListScreen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AppNavigation(startDestination: String,) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(route = "HomeScreen") {
            HomeScreen(navController)
        }
        composable(route = "PhotoPickerScreen") {
            PhotoPickerScreen(navController)
        }
        composable(route = "VerticalListScreen") {
            VerticalListScreen(navController)
        }
        composable(route = "HorizontalListScreen") {
            HorizontalListScreen(navController)
        }
        composable(route = "GridListScreen") {
            GridListScreen(navController)
        }

    }
}