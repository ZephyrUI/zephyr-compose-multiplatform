package org.zephyr.ui.foundation.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.zephyr.ui.foundation.choice_control.AnimatedRadioButton

@Composable
internal fun SimpleRadioButton() {

    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

            AnimatedRadioButton(
                selected = selectedIndex == 0,
                size = 36.dp,
                onClick = { selectedIndex = 0 }
            )

            AnimatedRadioButton(
                selected = selectedIndex == 1,
                size = 36.dp,
                onClick = { selectedIndex = 1 }
            )
        }
    }
}

@Composable
internal fun FillRadioButton() {

    var selectedIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {

            AnimatedRadioButton(
                selected = selectedIndex == 0,
                isOutline = false,
                size = 36.dp,
                onClick = { selectedIndex = 0 }
            )

            AnimatedRadioButton(
                selected = selectedIndex == 1,
                isOutline = false,
                size = 36.dp,
                onClick = { selectedIndex = 1 }
            )
        }
    }
}