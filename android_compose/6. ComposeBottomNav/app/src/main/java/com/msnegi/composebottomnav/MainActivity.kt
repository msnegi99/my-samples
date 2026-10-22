package com.msnegi.composebottomnav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.msnegi.composebottomnav.components.BottomBar
import com.msnegi.composebottomnav.components.TopBar
import com.msnegi.composebottomnav.navigation.*
import com.msnegi.composebottomnav.ui.theme.BottomNavWithSideBarTheme
import com.msnegi.composebottomnav.view.FavoriteScreen
import com.msnegi.composebottomnav.view.NearbyScreen
import com.msnegi.composebottomnav.view.ReservedScreen
import com.msnegi.composebottomnav.view.SavedScreen
import com.msnegi.composebottomnav.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavWithSideBarTheme {
                // A surface container using the 'background' color from the theme
                CompositionLocalProvider() {
                    AppScaffold()
                }
            }
        }
    }

    @Composable
    fun AppScaffold() {
        val viewModel: MainViewModel = viewModel()
        val navController = rememberNavController()

        val scope = rememberCoroutineScope()
        val currentScreen by viewModel.currentScreen.observeAsState()

        var topBar: @Composable () -> Unit = {
            TopBar(
                title = currentScreen!!.title,
                buttonIcon = Icons.Filled.Menu,
                onButtonClicked = {}
            )
        }

        val bottomBar: @Composable () -> Unit = {
            if (currentScreen is Screens.FavoriteScreens) {
                BottomBar(
                    navController = navController,
                    screens = screensInHomeFromBottomNav
                )
            }
        }

        Scaffold(
            topBar = {
                topBar()
            },
            bottomBar = {
                bottomBar()
            },
        ) { innerPadding ->
            NavigationHost(navController = navController, viewModel = viewModel)
        }
    }

    @Composable
    fun NavigationHost(navController: NavController, viewModel: MainViewModel) {
        NavHost(
            navController = navController as NavHostController,
            startDestination = Screens.FavoriteScreens.Favorite.route
        ) {
            composable(Screens.FavoriteScreens.Favorite.route) { FavoriteScreen(viewModel = viewModel) }
            composable(Screens.FavoriteScreens.NearBy.route) { NearbyScreen(viewModel = viewModel) }
            composable(Screens.FavoriteScreens.Reserved.route) { ReservedScreen(viewModel = viewModel) }
            composable(Screens.FavoriteScreens.Saved.route) { SavedScreen(viewModel = viewModel) }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        BottomNavWithSideBarTheme {
            AppScaffold()
        }
    }
}

