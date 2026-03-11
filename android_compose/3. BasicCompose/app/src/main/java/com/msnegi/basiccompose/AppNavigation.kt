package com.msnegi.basiccompose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msnegi.basiccompose.ui.screens.HomeScreen
import com.msnegi.basiccompose.ui.screens.LoginScreen
import com.msnegi.basiccompose.ui.screens.ProfileScreen
import com.msnegi.basiccompose.ui.screens.RegistrationScreen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AppNavigation(modifier: Modifier = Modifier,
                  startDestination: String,) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(route = "home") {
            HomeScreen()
        }
        composable(route = "login") {
            LoginScreen()
        }
        composable(route = "profile/{email}", arguments = listOf(navArgument("email"){
            type = NavType.StringType
        })) {
            ProfileScreen{
                navController.navigate("Login")
            }
        }
        composable(route = "registration") {
            RegistrationScreen()
        }
    }
}