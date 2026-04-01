package com.msnegi.mycomposesamples

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msnegi.mycomposesamples.ui.screens.ButtonsScreen
import com.msnegi.mycomposesamples.ui.screens.CardsScreen
import com.msnegi.mycomposesamples.ui.screens.DialogsScreen
import com.msnegi.mycomposesamples.ui.screens.TextScreen

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
        composable(route = "ButtonsScreen") {
            ButtonsScreen(navController)
        }
        composable(route = "CardsScreen") {
            CardsScreen(navController)
        }
        composable(route = "DialogsScreen") {
            DialogsScreen(navController)
        }
        composable(route = "TextScreen") {
            TextScreen(navController)
        }
    }
}