package com.indelible.gamepad.ui.screen.configuration

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indelible.gamepad.ui.core.ControllerType

@Composable
fun ControllerConfiguration(
    selectedControllerType: ControllerType,
    onSelectedControllerTypeChange: (ControllerType) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        ControllerType.entries.forEach{ controllerType ->
            val selectedItemModifier = if (selectedControllerType == controllerType)
                Modifier.border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            else Modifier

            ListItem(
                modifier = Modifier
                    .clickable { onSelectedControllerTypeChange(controllerType) }
                    .then(selectedItemModifier),
                headlineContent = {
                    Text(
                        text = controllerType.label,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingContent = {
                    RadioButton(
                        selected = controllerType == selectedControllerType,
                        onClick = {onSelectedControllerTypeChange(controllerType)},
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = controllerType.icon,
                        contentDescription = null
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}