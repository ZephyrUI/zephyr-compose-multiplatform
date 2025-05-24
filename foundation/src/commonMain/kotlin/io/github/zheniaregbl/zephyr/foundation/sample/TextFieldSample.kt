package io.github.zheniaregbl.zephyr.foundation.sample

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.github.zheniaregbl.zephyr.foundation.input.text_field.ZephyrTextField

@Composable
internal fun SimplyTextField() {

    var textFieldValue by remember { mutableStateOf("") }

    ZephyrTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textFieldValue,
        onValueChange = { textFieldValue = it },
        enabled = true,
        isError = false,
        placeholder = "Placeholder",
        textStyle = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    )
}