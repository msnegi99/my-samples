package com.msnegi.mycomposesamples.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.msnegi.mycomposesamples.R

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.FavoriteBorder

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.msnegi.mycomposesamples.ui.theme.Purple40
import com.msnegi.mycomposesamples.ui.theme.Purple80
import com.msnegi.mycomposesamples.ui.theme.typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {

    Button(
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.teal_700),
            disabledContainerColor = colorResource(id = R.color.teal_200),
            contentColor = Color.White,
        ),
        content = content
    )
}

@Preview
@Composable
private fun EnabledPrimaryButtonPreview() {
    PrimaryButton(onClick = {}) {
        Text(text = "Enabled")
    }
}

@Preview
@Composable
private fun DisabledPrimaryButtonPreview() {
    PrimaryButton(onClick = {}, enabled = false) {
        Text(text = "Disabled")
    }
}

@Preview
@Composable
private fun ToggleButtonPreview() {
    CustomToggleButton(false,onCheckedChange={})
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

@Composable
fun PrimaryIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    content: @Composable () -> Unit
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp),
        enabled = enabled,
        shape = shape,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = colorResource(id = R.color.teal_700),
            disabledContainerColor = colorResource(id = R.color.teal_200),
            contentColor = Color.White,
        ),
        content = content
    )
}

@Composable
fun PrimarySquareIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) = PrimaryIconButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    shape = RoundedCornerShape(8.dp),
    content = content
)

@Preview
@Composable
private fun PrimaryIconButtonPreview() {
    PrimaryIconButton(onClick = {}) {
        Icon(imageVector = Icons.Default.Search, contentDescription = "")
    }
}

@Preview
@Composable
private fun PrimarySquareIconButtonPreview() {
    PrimarySquareIconButton(onClick = {}) {
        Icon(imageVector = Icons.Default.Search, contentDescription = "")
    }
}


@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {

    OutlinedButton(
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        enabled = enabled,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = colorResource(id = R.color.teal_700),
            disabledContentColor = colorResource(id = R.color.teal_200).copy(
                alpha = SecondaryButtonTokens.DisabledLabelTextOpacity
            ),
        ),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = R.color.teal_700).copy(
                alpha = if (!enabled) SecondaryButtonTokens.DisabledContainerOpacity else 1f
            )
        ),
        content = content
    )
}

internal object SecondaryButtonTokens {
    const val DisabledContainerOpacity = 0.12f
    const val DisabledLabelTextOpacity = 0.38f
}

@Preview
@Composable
private fun EnabledSecondaryButtonPreview() {
    SecondaryButton(onClick = {}) {
        Text(text = "Enabled")
    }
}

@Preview
@Composable
private fun DisabledSecondaryButtonPreview() {
    SecondaryButton(onClick = {}, enabled = false) {
        Text(text = "Disabled")
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun AllButtons() {
    Text(text = "Buttons", style = typography.h5, modifier = Modifier.padding(8.dp))

    Row {
        Button(onClick = {}, modifier = Modifier.padding(8.dp)) { Text(text = "Main Button") }
        TextButton(onClick = {}, modifier = Modifier.padding(8.dp)) { Text(text = "Text Button") }
        TextButton(onClick = {}, modifier = Modifier.padding(8.dp), enabled = false) {
            Text(text = "Text Disabled")
        }
    }
    Row {
        Button(onClick = {}, modifier = Modifier.padding(8.dp), enabled = false) {
            Text(text = "Disabled")
        }
        Button(
            onClick = {},
            modifier = Modifier.padding(8.dp),
            elevation = ButtonDefaults.buttonElevation()
        ) {
            Text(text = "Flat")
        }
        Button(onClick = {}, modifier = Modifier.padding(8.dp), shape = RoundedCornerShape(12.dp)) {
            Text(text = "Rounded")
        }
    }
    Row {
        OutlinedButton(onClick = {}, modifier = Modifier.padding(8.dp)) { Text(text = "Outline") }
        Button(onClick = {}, modifier = Modifier.padding(8.dp)) {
            Row {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(text = "Icon Button")
            }
        }
        Button(onClick = {}, modifier = Modifier.padding(8.dp)) {
            Text(text = "Icon Button")
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
    // custom background buttons
    val outlineButtonColor =
        ButtonDefaults.outlinedButtonColors(
            contentColor = Purple80,
        )
    val mainButtonColor =
        ButtonDefaults.buttonColors(
            containerColor = Purple40,
            contentColor = MaterialTheme.colorScheme.surface
        )
    Row {
        OutlinedButton(colors = outlineButtonColor, onClick = {}, modifier = Modifier.padding(8.dp)) {
            Text(text = "Outline colors")
        }
        Button(colors = mainButtonColor, onClick = {}, modifier = Modifier.padding(8.dp)) {
            Text(text = "Custom colors")
        }
    }
    Row {
        val horizontalGradient =
            Brush.horizontalGradient(
                colors =
                    listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.inversePrimary),
                0f,
                250f
            )
        val verticalGradient =
            Brush.verticalGradient(
                colors =
                    listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.inversePrimary),
                startY = 0f,
                endY = 100f
            )
        Text(
            text = "Horizontal gradient",
            style = typography.body2.copy(color = Color.White),
            modifier =
                Modifier.padding(12.dp)
                    .clickable(onClick = {})
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = horizontalGradient)
                    .padding(12.dp)
        )
        Text(
            text = "Vertical gradient",
            style = typography.body1.copy(color = Color.White),
            modifier =
                Modifier.padding(12.dp)
                    .clickable(onClick = {})
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradient)
                    .padding(12.dp)
        )
    }

    val swipeButtonState = remember { mutableStateOf(SwipeButtonState.INITIAL) }
    val coroutineScope = rememberCoroutineScope()
    SwipeButton(
        onSwiped = {
            swipeButtonState.value = SwipeButtonState.SWIPED
            coroutineScope.launch {
                delay(2000)
                swipeButtonState.value = SwipeButtonState.COLLAPSED
            }
        },
        swipeButtonState = swipeButtonState.value,
        modifier = Modifier.fillMaxWidth().padding(16.dp).height(60.dp),
        iconPadding = PaddingValues(4.dp),
        shape = CircleShape,
    ) {
        Text(text = "PAY NOW", style = typography.h6.copy(fontSize = 16.sp))
    }
    SwipeButton(
        onSwiped = {},
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        iconPadding = PaddingValues(4.dp),
        swipeButtonState = SwipeButtonState.INITIAL,
        shape = CircleShape,
        enabled = false
    ) {
        Text(text = "Swipe", style = typography.body1)
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewButtons() {
    Column { AllButtons() }
}