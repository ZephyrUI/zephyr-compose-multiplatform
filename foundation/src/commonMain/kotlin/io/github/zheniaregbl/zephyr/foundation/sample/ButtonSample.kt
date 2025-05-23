package io.github.zheniaregbl.zephyr.foundation.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.zheniaregbl.zephyr.foundation.button.ZephyrButton

@Composable
internal fun SimplyButton() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ZephyrButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            text = "Zephyr button",
            onClick = { /* Do something */ }
        )
    }
}

@Composable
internal fun SimplyOutlineButton() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ZephyrButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            text = "Zephyr button",
            isOutline = true,
            onClick = { /* Do something */ }
        )
    }
}