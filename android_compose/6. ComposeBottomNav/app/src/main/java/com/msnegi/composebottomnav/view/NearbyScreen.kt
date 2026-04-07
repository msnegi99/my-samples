package com.msnegi.composebottomnav.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.msnegi.composebottomnav.navigation.Screens
import com.msnegi.composebottomnav.viewmodel.MainViewModel

@Composable
fun NearbyScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    viewModel.setCurrentScreen(Screens.FavoriteScreens.NearBy)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Near by.", style = MaterialTheme.typography.h4)
    }
}