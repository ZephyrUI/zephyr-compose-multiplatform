package io.github.zheniaregbl.zephyr.foundation.choice_control

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.zheniaregbl.zephyr.core.theme.ZephyrDisableColor
import io.github.zheniaregbl.zephyr.core.theme.ZephyrPrimaryColor

/**
 * A customizable animated switch component with smooth transitions and flexible styling.
 * @param modifier Modifier to apply custom layout or styling to the switch.
 * @param checked Indicates whether the switch is in the "on" (`true`) or "off" (`false`) state.
 * @param enabled Controls whether the switch is interactive. If `false`, the switch is disabled and does not respond to user input.
 * @param width The width of the switch, defining its horizontal size.
 * @param height The height of the switch, defining its vertical size.
 * @param colors An instance of [ZephyrSwitchColor] to customize the switch's color palette, including the track and thumb colors.
 * @param animationSpec Specifies the animation for the smooth transition of the thumb position when the switch state changes.
 * @param onCheckedChange Callback invoked when the switch state changes. Receives the new state as a Boolean.
 *
 * @sample io.github.zheniaregbl.zephyr.foundation.sample.SimpleSwitch
 */
@Composable
fun AnimatedSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    enabled: Boolean = true,
    width: Dp = 48.dp,
    height: Dp = 24.dp,
    colors: ZephyrSwitchColor = ZephyrSwitchColor(),
    animationSpec: AnimationSpec<Float> = tween(
        durationMillis = 200,
        easing = FastOutSlowInEasing
    ),
    onCheckedChange: (Boolean) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val thumbSize = height * 0.83f

    val thumbPosition by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = animationSpec
    )
    val currentTrackColor = when {
        !enabled -> colors.disabledTrackColor
        checked -> colors.checkedTrackColor
        else -> colors.trackColor
    }

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(height / 2))
            .background(currentTrackColor)
            .clickable(
                enabled = enabled,
                role = Role.Switch,
                indication = null,
                interactionSource = interactionSource,
                onClick = { onCheckedChange(!checked) }
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            val canvasWidth = size.width
            val canvasHeight = size.height
            val thumbRadius = thumbSize.toPx() / 2

            val startX = thumbRadius + thumbRadius * 0.25f
            val endX = canvasWidth - thumbRadius * 1.25f
            val thumbX = startX + (endX - startX) * thumbPosition

            drawCircle(
                color = if (enabled) colors.thumbColor else colors.disabledThumbColor,
                radius = thumbRadius,
                center = Offset(thumbX, canvasHeight / 2)
            )
        }
    }
}

@Immutable
class ZephyrSwitchColor(
    val thumbColor: Color = Color.White,
    val trackColor: Color = ZephyrDisableColor,
    val checkedTrackColor: Color = ZephyrPrimaryColor,
    val disabledTrackColor: Color = Color(0xFFB8B8B8),
    val disabledThumbColor: Color = Color(0xFFE3E3E3)
) {

    fun copy(
        thumbColor: Color = this.thumbColor,
        trackColor: Color = this.trackColor,
        checkedTrackColor: Color = this.checkedTrackColor,
        disabledTrackColor: Color = this.disabledTrackColor,
        disabledThumbColor: Color = this.disabledThumbColor
    ) = ZephyrSwitchColor(
        thumbColor.takeOrElse { this.thumbColor },
        trackColor.takeOrElse { this.trackColor },
        checkedTrackColor.takeOrElse { this.checkedTrackColor },
        disabledTrackColor.takeOrElse { this.disabledTrackColor },
        disabledThumbColor.takeOrElse { this.disabledThumbColor }
    )

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other == null || other !is ZephyrSwitchColor) return false

        if (thumbColor != other.thumbColor) return false
        if (trackColor != other.trackColor) return false
        if (checkedTrackColor != other.checkedTrackColor) return false
        if (disabledTrackColor != other.disabledTrackColor) return false
        if (disabledThumbColor != other.disabledThumbColor) return false

        return true
    }

    override fun hashCode(): Int {

        var result = thumbColor.hashCode()

        result = 31 * result + trackColor.hashCode()
        result = 31 * result + checkedTrackColor.hashCode()
        result = 31 * result + disabledTrackColor.hashCode()
        result = 31 * result + disabledThumbColor.hashCode()

        return result
    }
}