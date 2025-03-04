package com.indelible.gamepad

import kotlin.math.PI
import kotlin.math.atan2

//Convert the angle to the user coordinate (degree)
fun angleToUserCoordinate(y: Float, x: Float): Float{
    var angle = (atan2(-y, x) *(180/ PI)).toFloat()
    if (y > 0){
        angle += 360
    }
    return angle/15
}