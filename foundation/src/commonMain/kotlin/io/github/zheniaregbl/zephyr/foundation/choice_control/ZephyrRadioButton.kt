package io.github.zheniaregbl.zephyr.foundation.choice_control

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.zheniaregbl.zephyr.core.theme.ZephyrDisableColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrPrimaryColor

/**
 * A customizable animated radio button that supports outline and solid styles, with scaling animation.
 * @param modifier Modifier to apply custom layout or styling to the radio button.
 * @param selected Indicates whether the radio button is selected `true` or not `false`.
 * @param enabled Controls whether the radio button is interactive. If `false`, it appears disabled and does not respond to user input.
 * @param isOutline Determines the style of the radio button:
 * - `true`: The radio button is displayed with an outline style (transparent center with a border).
 * - `false`: The radio button is displayed with an outline style, only when selected the border size increases.
 * @param size The size of the radio button, which defines the total diameter of the button.
 * @param color The color of the radio button when it is enabled and selected.
 * @param disableColor The color of the radio button when it is disabled.
 * @param animationSpec Specifies the animation for the scaling effect when the selection state changes. Defaults to a 100ms ease-out tween animation.
 * @param onClick Callback invoked when the radio button is clicked.
 *
 * @sample io.github.zheniaregbl.zephyr.foundation.sample.SimpleRadioButton
 */
@Composable
fun ZephyrRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    enabled: Boolean = true,
    isOutline: Boolean = true,
    size: Dp = 24.dp,
    color: Color = ZephyrPrimaryColor,
    disableColor: Color = ZephyrDisableColor,
    animationSpec: AnimationSpec<Dp> = tween(
        durationMillis = 100,
        easing = EaseOut
    ),
    onClick: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val animateBorderWidth by animateDpAsState(
        targetValue = if (selected) size / 2 else size * 0.15f,
        animationSpec = animationSpec
    )
    val animateDotRadius by animateDpAsState(
        targetValue = if (selected) size / 4 else 0.dp,
        animationSpec = animationSpec
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable(
                enabled = enabled,
                role = Role.RadioButton,
                indication = null,
                interactionSource = interactionSource,
                onClick = onClick
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            drawCircle(
                color = if (enabled) color else disableColor,
                radius = size.toPx() / 2,
                style = if (isOutline) Stroke(width = (size * 0.15f).toPx())
                else Stroke(width = animateBorderWidth.toPx())
            )

            if (isOutline) {
                drawCircle(
                    color = if (enabled) color else disableColor,
                    radius = animateDotRadius.toPx()
                )
            }
        }
    }
}