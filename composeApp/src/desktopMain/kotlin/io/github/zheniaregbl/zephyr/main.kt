package io.github.zheniaregbl.zephyr

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "zephyr-ui",
    ) {
        App()
    }
}