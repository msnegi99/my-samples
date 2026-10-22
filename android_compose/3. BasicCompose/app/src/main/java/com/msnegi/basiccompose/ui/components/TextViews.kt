package com.msnegi.basiccompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MyTextView(data1: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*var isChecked by remember { mutableStateOf(false) }
        CheckBoxNormal(checked = isChecked,
            onCheckedChange = { newCheckedState ->
                isChecked = newCheckedState
            }
        )*/

        RangeSliderNormal()
    }
}