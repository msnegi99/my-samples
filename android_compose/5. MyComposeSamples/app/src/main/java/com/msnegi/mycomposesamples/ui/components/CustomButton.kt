package com.msnegi.mycomposesamples.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CustomButtonWithModifier(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(8.dp),
    ) {
        Text(text = text)
    }
}

@Composable
fun CustomToggleButton(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onCheckedChange(!isChecked) },
        modifier = modifier,
        colors = if (isChecked) {
            ButtonDefaults.buttonColors(Color.Green)
        } else {
            ButtonDefaults.buttonColors( Color.Red)
        }
    ) {
        Text(text = if (isChecked) "ON" else "OFF")
    }
}
