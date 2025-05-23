package io.github.zheniaregbl.zephyr

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.zheniaregbl.zephyr.foundation.button.ZephyrButton
import io.github.zheniaregbl.zephyr.foundation.choice_control.ZephyrCheckbox
import io.github.zheniaregbl.zephyr.foundation.choice_control.ZephyrRadioButton
import io.github.zheniaregbl.zephyr.foundation.choice_control.ZephyrSwitch

@Composable
internal fun App() {

    var checked by remember { mutableStateOf(false) }
    var selectedRadioIndex by remember { mutableIntStateOf(0) }
    var selectedFillRadioIndex by remember { mutableIntStateOf(0) }
    var isChecked by remember { mutableStateOf(false) }
    var isCheckedOutline by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {

        item {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Switch",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    ZephyrSwitch(
                        checked = checked,
                        width = 60.dp,
                        height = 30.dp,
                        onCheckedChange = { checked = it }
                    )

                    ZephyrSwitch(
                        checked = false,
                        enabled = false,
                        width = 60.dp,
                        height = 30.dp,
                        onCheckedChange = { }
                    )
                }
            }
        }

        item {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Radiobutton",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    ZephyrRadioButton(
                        selected = selectedRadioIndex == 0,
                        size = 26.dp,
                        onClick = { selectedRadioIndex = 0 }
                    )

                    ZephyrRadioButton(
                        selected = selectedRadioIndex == 1,
                        size = 26.dp,
                        onClick = { selectedRadioIndex = 1 }
                    )

                    ZephyrRadioButton(
                        selected = true,
                        enabled = false,
                        size = 26.dp,
                        onClick = { }
                    )

                    ZephyrRadioButton(
                        selected = false,
                        enabled = false,
                        size = 26.dp,
                        onClick = { }
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    ZephyrRadioButton(
                        selected = selectedFillRadioIndex == 0,
                        isOutline = false,
                        size = 26.dp,
                        onClick = { selectedFillRadioIndex = 0 }
                    )

                    ZephyrRadioButton(
                        selected = selectedFillRadioIndex == 1,
                        isOutline = false,
                        size = 26.dp,
                        onClick = { selectedFillRadioIndex = 1 }
                    )

                    ZephyrRadioButton(
                        selected = true,
                        enabled = false,
                        isOutline = false,
                        size = 26.dp,
                        onClick = { }
                    )

                    ZephyrRadioButton(
                        selected = false,
                        enabled = false,
                        isOutline = false,
                        size = 26.dp,
                        onClick = { }
                    )
                }
            }
        }

        item {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Checkbox",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    ZephyrCheckbox(
                        isChecked = isChecked,
                        size = 26.dp,
                        cornerRadius = 4.dp,
                        onCheckedChange = { isChecked = it }
                    )

                    ZephyrCheckbox(
                        isChecked = true,
                        enabled = false,
                        size = 26.dp,
                        cornerRadius = 4.dp,
                        onCheckedChange = { }
                    )

                    ZephyrCheckbox(
                        isChecked = false,
                        enabled = false,
                        size = 26.dp,
                        cornerRadius = 4.dp,
                        onCheckedChange = { }
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    ZephyrCheckbox(
                        isChecked = isCheckedOutline,
                        isOutline = true,
                        size = 26.dp,
                        cornerRadius = 4.dp,
                        onCheckedChange = { isCheckedOutline = it }
                    )

                    ZephyrCheckbox(
                        isChecked = true,
                        isOutline = true,
                        enabled = false,
                        size = 26.dp,
                        cornerRadius = 4.dp,
                        onCheckedChange = { }
                    )

                    ZephyrCheckbox(
                        isChecked = false,
                        isOutline = true,
                        enabled = false,
                        size = 26.dp,
                        cornerRadius = 4.dp,
                        onCheckedChange = { }
                    )
                }
            }
        }

        item {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Button",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                    ZephyrButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Zephyr button",
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        onClick = { }
                    )

                    ZephyrButton(
                        modifier = Modifier.fillMaxWidth(),
                        isOutline = true,
                        text = "Zephyr button",
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        onClick = { }
                    )

                    ZephyrButton(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        text = "Zephyr button",
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        onClick = { }
                    )

                    ZephyrButton(
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        isOutline = true,
                        text = "Zephyr button",
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        ),
                        onClick = { }
                    )
                }
            }
        }
    }
}