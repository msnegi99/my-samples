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
import com.msnegi.mycomposesamples.ui.screens.CheckboxScreen
import com.msnegi.mycomposesamples.ui.screens.DatePickerScreen
import com.msnegi.mycomposesamples.ui.screens.DialogsScreen
import com.msnegi.mycomposesamples.ui.screens.DropdownScreen
import com.msnegi.mycomposesamples.ui.screens.ImageScreen
import com.msnegi.mycomposesamples.ui.screens.InputTextScreen
import com.msnegi.mycomposesamples.ui.screens.RadioGroupScreen
import com.msnegi.mycomposesamples.ui.screens.TextScreen
import com.msnegi.mycomposesamples.ui.screens.TimePickerScreen

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
        composable(route = "InputTextScreen") {
            InputTextScreen(navController)
        }
        composable(route = "CheckboxScreen") {
            CheckboxScreen(navController)
        }
        composable(route = "RadioGroupScreen") {
            RadioGroupScreen(navController)
        }
        composable(route = "DropdownScreen") {
            DropdownScreen(navController)
        }
        composable(route = "DatePickerScreen") {
            DatePickerScreen(navController)
        }
        composable(route = "TimePickerScreen") {
            TimePickerScreen(navController)
        }
        composable(route = "ImageScreen") {
            ImageScreen(navController)
        }
    }
}