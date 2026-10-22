package com.msnegi.basiccompose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CardNormal()
{
    val cardText = "Card content"
    val enabled = true
    val cornerRadius = 8f
    val useCustomColors = false
    val containerColor = Color(0xFFE8DEF8)
    val contentColor = Color(0xFF1D192B)
    val elevationValue = 4f
    val useBorder = false
    val borderWidth = 2f
    val borderColor = Color(0xFF6750A4)
    val paddingValue = 0f
    var isToggled by remember { mutableStateOf(false) }

    Card(
        onClick = { isToggled = !isToggled },
        modifier = Modifier.size(width = 180.dp, height = 100.dp),
        enabled = enabled,
        shape = RoundedCornerShape(cornerRadius.dp),
        colors = if (useCustomColors) {
            CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        } else {
            CardDefaults.cardColors()
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevationValue.dp
        ),
        border = if (useBorder) {
            BorderStroke(width = borderWidth.dp, color = borderColor)
        } else {
            null
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(cardText)
        }
    }
}