package com.indelible.gamepad.ui.core

data class JoystickPosition(
    val norm: Float = 0f,
    val angle: Float = 0f
){
    fun toEncodedByte(): Byte {
        val encodedNorm = norm*7
        val encodedAngle = (angle/360)*31
        return encodedAngle.toInt().shl(3).or(encodedNorm.toInt()).toByte()
    }
}