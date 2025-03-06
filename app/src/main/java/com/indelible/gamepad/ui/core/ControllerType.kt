package com.indelible.gamepad.ui.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ControlCamera
import androidx.compose.material.icons.outlined.ElectricMeter
import androidx.compose.ui.graphics.vector.ImageVector

enum class ControllerType(val label: String, val icon: ImageVector) {
    DIGITAL(label = "Digital Controller", icon = Icons.Default.ControlCamera),
    ACCELEROMETER(label = "Accelerometer Controller", icon = Icons.Outlined.ElectricMeter)
}