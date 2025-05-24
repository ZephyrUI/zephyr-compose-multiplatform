package io.github.zheniaregbl.zephyr.foundation.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastCoerceIn
import kotlinx.coroutines.launch
import io.github.zheniaregbl.zephyr.core.theme.ZephyrDisableColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrPrimaryColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrSecondaryColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrTertiaryOne

/**
 * The element for displaying the button. The button has an indentation animation when pressed, as well as a color change animation.
 * @param modifier The modifier to be applied to the layout.
 * @param text The text to display on the button.
 * @param enabled The button's activity status.
 * @param isOutline If `true`, the button will be rendered in an outline style (transparent background with a border).
 * @param colors Object for getting button colors in different states. See [ZephyrButtonColor].
 * @param cornerRadius Rounding the edges of the button.
 * @param softness The scale factor when the button is pressed. Must be between `0f` and `1f`.
 * @param onClick The lambda to be executed when the button is clicked.
 *
 * @sample io.github.zheniaregbl.zephyr.foundation.sample.SimplyButton
 * */
@Composable
fun ZephyrButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isOutline: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: ZephyrButtonColor = ZephyrButtonColor(),
    cornerRadius: Dp = 8.dp,
    softness: Float = 0.98f,
    onClick: () -> Unit,
) {

    val scope = rememberCoroutineScope()

    val interactionSource = remember { MutableInteractionSource() }
    var isPressed by remember { mutableStateOf(false) }
    var isHovered by remember { mutableStateOf(false) }

    val scale = remember { Animatable(1f) }

    val buttonSoftness by remember { mutableFloatStateOf(softness.fastCoerceIn(0.0f, 1.0f)) }

    val buttonColor by remember {
        derivedStateOf {
            when {
                isOutline && isPressed -> colors.pressedColor.copy(alpha = 0.1f)
                isOutline -> Color.Transparent
                !enabled -> colors.disableColor
                isPressed -> colors.pressedColor
                isHovered -> colors.hoverColor
                else -> colors.inactiveColor
            }
        }
    }

    val borderColor by remember {
        derivedStateOf {
            when {
                !enabled -> colors.disableColor
                isPressed -> colors.pressedColor
                isHovered -> colors.hoverColor
                else -> colors.inactiveColor
            }
        }
    }

    val buttonTextColor by remember {
        derivedStateOf {
            when {
                isOutline && !enabled -> colors.disableColor
                isOutline && isPressed -> colors.pressedColor
                isOutline && isHovered -> colors.hoverColor
                isOutline && !isPressed -> colors.inactiveColor
                else -> colors.textColor
            }
        }
    }

    val animatedButtonColor by animateColorAsState(
        targetValue = buttonColor,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        label = "Button color"
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = borderColor,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        label = "Border color"
    )

    val animatedTextColor by animateColorAsState(
        targetValue = buttonTextColor,
        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing),
        label = "Button text color"
    )

    LaunchedEffect(interactionSource) {

        interactionSource.interactions.collect { interaction ->

            when (interaction) {

                is HoverInteraction.Enter -> isHovered = true

                is HoverInteraction.Exit -> isHovered = false

                is PressInteraction.Press -> {
                    isPressed = true
                    scope.launch {
                        scale.animateTo(
                            buttonSoftness,
                            animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
                        )
                    }
                }

                is PressInteraction.Release, is PressInteraction.Cancel -> {
                    isPressed = false
                    scope.launch {
                        scale.animateTo(
                            1f,
                            animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.matchParentSize()) {

            drawRoundRect(
                color = animatedButtonColor,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )

            if (isOutline) {
                drawRoundRect(
                    color = animatedBorderColor,
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(
                    horizontal = 15.dp,
                    vertical = 11.dp
                ),
            text = text,
            style = textStyle.copy(color = if (isOutline) animatedTextColor else buttonTextColor)
        )
    }
}

/**
 * A class containing the background and text colors of the button in different states.
 * @param inactiveColor Button color when inactive.
 * @param hoverColor Button color when hover
 * @param pressedColor Button color when pressed.
 * @param disableColor Button color when disabled.
 * @param textColor Button text color.
 * */
@Immutable
class ZephyrButtonColor(
    val inactiveColor: Color = ZephyrTertiaryOne,
    val hoverColor: Color = ZephyrPrimaryColor,
    val pressedColor: Color = ZephyrSecondaryColor,
    val disableColor: Color = ZephyrDisableColor,
    val textColor: Color = Color.White
) {

    fun copy(
        inactiveColor: Color = this.inactiveColor,
        hoverColor: Color = this.hoverColor,
        pressedColor: Color = this.pressedColor,
        disableColor: Color = this.pressedColor,
        textColor: Color = this.textColor
    ) = ZephyrButtonColor(
        inactiveColor.takeOrElse { this.inactiveColor },
        hoverColor.takeOrElse { this.hoverColor },
        pressedColor.takeOrElse { this.pressedColor },
        disableColor.takeOrElse { this.disableColor },
        textColor.takeOrElse { this.textColor },
    )

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other == null || other !is ZephyrButtonColor) return false

        if (inactiveColor != other.inactiveColor) return false
        if (hoverColor != other.hoverColor) return false
        if (pressedColor != other.pressedColor) return false
        if (disableColor != other.disableColor) return false
        if (textColor != other.textColor) return false

        return true
    }

    override fun hashCode(): Int {

        var result = inactiveColor.hashCode()

        result = 31 * result + hoverColor.hashCode()
        result = 31 * result + pressedColor.hashCode()
        result = 31 * result + disableColor.hashCode()
        result = 31 * result + textColor.hashCode()

        return result
    }
}