package com.indelible.gamepad

import com.indelible.gamepad.ui.core.JoystickPosition
import kotlin.math.PI
import kotlin.math.atan2

//Convert the angle to the user coordinate (degree)
fun angleToUserCoordinate(y: Float, x: Float): Float{
    var angle = (atan2(-y, x) *(180/ PI)).toFloat() + 90f
    if (y > 0 && x < 0){
        angle += 360
    }
    return angle/15
}

// Gonna be replaced to handle joystick position which is moved at the same time
fun createJoystickByteArray(position: JoystickPosition, joystickId: Int): ByteArray {
    return if (joystickId == 0)
        byteArrayOf(255.toByte(), 1, 2, 1, 1, 0, position.toEncodedByte(), 0)
    else
        byteArrayOf(255.toByte(), 1, 2, 1, 1, 0, 0, position.toEncodedByte())
}