package com.msnegi.basiccompose.ui.components

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CheckBoxNormal(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier, // Add a modifier parameter
    enabled: Boolean = true,
    useCustomColors: Boolean = false,
    checkedColor: Color = Color.Magenta,
    uncheckedColor: Color = Color.LightGray,
    checkmarkColor: Color = Color.White,
    disabledColor: Color = Color.Gray
){
    val checkboxColors = if (useCustomColors) {
        CheckboxDefaults.colors(
            checkedColor = checkedColor,
            uncheckedColor = uncheckedColor,
            checkmarkColor = checkmarkColor,
            disabledCheckedColor = disabledColor,
            disabledUncheckedColor = disabledColor
        )
    } else {
        CheckboxDefaults.colors()
    }

    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = checkboxColors
    )
}