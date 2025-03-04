package com.indelible.gamepad.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.indelible.gamepad.angleToUserCoordinate
import com.indelible.gamepad.ui.core.JoystickPosition
import kotlin.math.hypot
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun Joystick(
    modifier: Modifier = Modifier,
    updatePosition: (JoystickPosition) -> Unit
){
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Box(modifier = modifier) {
        Surface(
            modifier = Modifier
                .size(136.dp),
            shape = CircleShape,
            border = CardDefaults.outlinedCardBorder()
        ) {}
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .requiredSize(80.dp)
                .align(Alignment.Center)
                .clip(CircleShape)
                .border(1.dp, Color.Black, CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            offsetX = 0f
                            offsetY = 0f
                            updatePosition(JoystickPosition())
                        }
                    ) { change, dragAmount ->
                        change.consume()

                        val circleRadius = min(size.width, size.height) / 2
                        val newOffsetX = offsetX + dragAmount.x
                        val newOffsetY = offsetY + dragAmount.y
                        val distance = hypot(newOffsetX, newOffsetY)

                        updatePosition(
                            JoystickPosition(
                                norm = min(1f,distance/circleRadius),
                                angle = angleToUserCoordinate(newOffsetY, newOffsetX)
                            )
                        )

                        if (distance <= circleRadius) {
                            // Inside the circle: update position normally
                            offsetX = newOffsetX
                            offsetY = newOffsetY
                        } else {
                            // Outside the circle: constrain to boundary
                            val scale = circleRadius / distance
                            offsetX = newOffsetX * scale
                            offsetY = newOffsetY * scale
                        }
                    }
                }
        )
    }
}