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
import io.github.zheniaregbl.zephyr.foundation.choice_control.AnimatedCheckbox

@Composable
internal fun SimpleCheckbox() {

    var isChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedCheckbox(
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            size = 24.dp,
            cornerRadius = 4.dp
        )
    }
}

@Composable
internal fun OutlineCheckbox() {

    var isChecked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedCheckbox(
            isChecked = isChecked,
            isOutline = true,
            size = 24.dp,
            cornerRadius = 4.dp,
            onCheckedChange = { isChecked = it }
        )
    }
}