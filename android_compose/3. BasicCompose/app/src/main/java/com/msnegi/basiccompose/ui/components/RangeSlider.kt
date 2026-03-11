package com.msnegi.basiccompose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RangeSliderNormal() {
    val startValue = 0.2f
    val endValue = 0.8f
    val enabled = true

    var sliderPosition by remember(startValue, endValue) { mutableStateOf(startValue..endValue) }

    Column(modifier = Modifier.padding(16.dp)) {
        RangeSlider(
            modifier = Modifier.width(200.dp),
            value = sliderPosition,
            onValueChange = { range -> sliderPosition = range },
            valueRange = 0f..1f,
            enabled = enabled
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Selected range: ${(sliderPosition.start * 100).toInt()}% - ${(sliderPosition.endInclusive * 100).toInt()}%",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}