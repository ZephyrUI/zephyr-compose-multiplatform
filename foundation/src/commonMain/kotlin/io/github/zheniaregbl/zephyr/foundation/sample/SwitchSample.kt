package io.github.zheniaregbl.zephyr.foundation.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.zheniaregbl.zephyr.foundation.choice_control.AnimatedSwitch

@Composable
fun SimpleSwitch() {

    var checked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedSwitch(
            checked = checked,
            width = 60.dp,
            height = 30.dp,
            onCheckedChange = { checked = it }
        )
    }
}