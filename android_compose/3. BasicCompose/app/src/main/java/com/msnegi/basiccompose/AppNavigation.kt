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
import com.msnegi.basiccompose.ui.screens.DetailScreen
import com.msnegi.basiccompose.ui.screens.ForgotScreen
import com.msnegi.basiccompose.ui.screens.HomeScreen
import com.msnegi.basiccompose.ui.screens.LoginScreen
import com.msnegi.basiccompose.ui.screens.ProfileScreen
import com.msnegi.basiccompose.ui.screens.RegistrationScreen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AppNavigation(startDestination: String,) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(route = "home") {
            HomeScreen(navController)
        }
        composable(route = "login") {
            LoginScreen(navController)
        }
        composable(route = "profile/{email}", arguments = listOf(navArgument("email"){
            type = NavType.StringType
        })) {backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            ProfileScreen(
                email = email ?: "No email",
                onClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = "forgot") {
            ForgotScreen(navController)
        }
        composable(route = "registration") {
            RegistrationScreen(navController)
        }
        composable(route = "detailsrc/{value}",  arguments = listOf(navArgument("value"){
            type = NavType.StringType
        })) { backStackEntry ->
            val value = backStackEntry.arguments?.getString("value")
            DetailScreen(navController, value)
        }

    }
}