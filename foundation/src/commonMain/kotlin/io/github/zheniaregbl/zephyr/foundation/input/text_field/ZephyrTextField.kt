package io.github.zheniaregbl.zephyr.foundation.input.text_field

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.zheniaregbl.zephyr.core.theme.ZephyrDisableBackgroundColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrDisableColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrErrorColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrPrimaryColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrTertiaryOne
import io.github.zheniaregbl.zephyr.core.theme.ZephyrTertiaryTwo

/**
 * Text field component for user input. Supports hover, focus, error, and disabled states with smooth color animations.
 * @param modifier The modifier to be applied to the layout.
 * @param value The current text value of the text field.
 * @param onValueChange Callback invoked when the text value changes.
 * @param enabled Whether the text field is interactive. If `false`, the field is disabled.
 * @param readOnly Whether the text field is read-only. If `true`, input is prevented but selection is allowed.
 * @param isError Whether the text field is in an error state, affecting its appearance.
 * @param placeholder The placeholder text displayed when the field is empty.
 * @param textStyle The text style applied to the input text and placeholder.
 * @param colors Object defining colors for different states of the text field. See [ZephyrTextFieldColor].
 * @param cornerRadius The corner radius for the text field's background and border.
 *
 * @sample io.github.zheniaregbl.zephyr.foundation.sample.SimplyTextField
 */
@Composable
fun ZephyrTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    placeholder: String? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: ZephyrTextFieldColor = ZephyrTextFieldColor(),
    cornerRadius: Dp = 8.dp
) {

    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }

    val backgroundColor by remember {
        derivedStateOf {
            when {
                !enabled -> colors.disabledBackgroundColor
                isError -> colors.errorBackgroundColor
                isFocused -> colors.focusedBackgroundColor
                isHovered -> colors.hoveredBackgroundColor
                else -> colors.unfocusedBackgroundColor
            }
        }
    }

    val borderColor by remember {
        derivedStateOf {
            when {
                !enabled -> colors.disabledBorderColor
                isError -> colors.errorBorderColor
                isFocused -> colors.focusedBorderColor
                isHovered -> colors.hoveredBorderColor
                else -> colors.unfocusedBorderColor
            }
        }
    }

    val textColor by remember {
        derivedStateOf {
            when {
                !enabled -> colors.disabledTextColor
                isError -> colors.errorTextColor
                isFocused -> colors.focusedTextColor
                isHovered -> colors.hoveredTextColor
                else -> colors.unfocusedTextColor
            }
        }
    }

    val placeholderColor by remember {
        derivedStateOf {
            when {
                !enabled -> colors.disabledPlaceholderColor
                isError -> colors.errorPlaceholderColor
                isFocused -> colors.focusedPlaceholderColor
                isHovered -> colors.hoveredPlaceholderColor
                else -> colors.unfocusedPlaceholderColor
            }
        }
    }

    val animatedBackgroundColor by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        label = "Text field background color"
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = borderColor,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        label = "Text field border color"
    )

    val animatedTextColor by animateColorAsState(
        targetValue = textColor,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        label = "Text field text color"
    )

    val animatedPlaceholderColor by animateColorAsState(
        targetValue = placeholderColor,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        label = "Text field placeholder color"
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> isHovered = true
                is HoverInteraction.Exit -> isHovered = false
                is FocusInteraction.Focus -> isFocused = true
                is FocusInteraction.Unfocus -> isFocused = false
            }
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .hoverable(interactionSource)
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {

            drawRoundRect(
                color = animatedBackgroundColor,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )

            drawRoundRect(
                color = animatedBorderColor,
                cornerRadius = CornerRadius(cornerRadius.toPx()),
                style = Stroke(width = 4.dp.toPx())
            )
        }

        CompositionLocalProvider(
            LocalTextSelectionColors provides TextSelectionColors(
                handleColor = if (isError) colors.textSelectionColors.errorHandleColor
                else colors.textSelectionColors.handleColor,
                backgroundColor = if (isError) colors.textSelectionColors.errorBackgroundColor
                else colors.textSelectionColors.backgroundColor
            )
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 13.5.dp),
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                readOnly = readOnly,
                textStyle = textStyle.copy(color = animatedTextColor),
                interactionSource = interactionSource,
                cursorBrush = SolidColor(if (isError) colors.errorCursorColor else colors.cursorColor),
                singleLine = true,
                decorationBox =  { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                style = textStyle.copy(color = animatedPlaceholderColor)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

class ZephyrTextFieldColor(
    val hoveredTextColor: Color = ZephyrTertiaryOne,
    val focusedTextColor: Color = Color(0xFF424242),
    val unfocusedTextColor: Color = Color(0xFF424242),
    val disabledTextColor: Color = ZephyrDisableColor,
    val errorTextColor: Color = ZephyrErrorColor,
    val hoveredBackgroundColor: Color = Color.White,
    val focusedBackgroundColor: Color = Color.White,
    val unfocusedBackgroundColor: Color = Color.White,
    val disabledBackgroundColor: Color = ZephyrDisableBackgroundColor,
    val errorBackgroundColor: Color = Color.White,
    val hoveredBorderColor: Color = ZephyrTertiaryOne,
    val focusedBorderColor: Color = ZephyrPrimaryColor,
    val unfocusedBorderColor: Color = ZephyrTertiaryTwo,
    val disabledBorderColor: Color = ZephyrDisableColor,
    val errorBorderColor: Color = ZephyrErrorColor,
    val hoveredPlaceholderColor: Color = ZephyrTertiaryOne.copy(alpha = 0.4f),
    val focusedPlaceholderColor: Color = ZephyrPrimaryColor.copy(alpha = 0.4f),
    val unfocusedPlaceholderColor: Color = ZephyrTertiaryTwo.copy(alpha = 0.4f),
    val disabledPlaceholderColor: Color = ZephyrDisableColor.copy(alpha = 0.65f),
    val errorPlaceholderColor: Color = ZephyrErrorColor.copy(alpha = 0.4f),
    val cursorColor: Color = ZephyrPrimaryColor,
    val errorCursorColor: Color = ZephyrErrorColor,
    val textSelectionColors: ZephyrTextSelectionColors = ZephyrTextSelectionColors()
)

class ZephyrTextSelectionColors(
    val handleColor: Color = ZephyrPrimaryColor,
    val backgroundColor: Color = ZephyrPrimaryColor.copy(alpha = 0.4f),
    val errorHandleColor: Color = ZephyrErrorColor,
    val errorBackgroundColor: Color = ZephyrErrorColor.copy(alpha = 0.4f)
)