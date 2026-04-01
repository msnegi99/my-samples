package com.msnegi.composeverticallist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.msnegi.mycomposesamples.ui.screens.DetailScreen
import com.msnegi.mycomposesamples.ui.screens.HomeScreen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AppNavigation(startDestination: String,) {

    val imageId = arrayOf(
        R.drawable.p1,
        R.drawable.p2,
        R.drawable.p3,
        R.drawable.p4,
        R.drawable.p5,
        R.drawable.p6
    )

    val names = arrayOf(
        "Peperoni",
        "Vegan",
        "FourCheese",
        "Margaritta",
        "American",
        "Mexican"
    )

    val ingredients = arrayOf(
        "Tomato sos, cheese, oregano, peperoni",
        "Tomato sos, cheese, oregano, spinach, green paprika, rukola",
        "Tomato sos, oregano, mozzarella, goda, parmesan, cheddar",
        "Tomato sos, cheese, oregano, bazillion",
        "Tomato sos, cheese, oregano, green paprika, red beans",
        "Tomato sos, cheese, oregano, corn, jalapeno, chicken",
    )

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ){
        composable(route = "home") {
            HomeScreen(imageId, names, ingredients, navController)
        }
        composable(route = "detailsrc/{index}",  arguments = listOf(navArgument("index"){
            type = NavType.IntType
        })) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index")
            DetailScreen(
                photos = imageId,
                names = names,
                ingredients = ingredients,
                index = index as Int?,
                navController = navController
            )
        }

    }
}