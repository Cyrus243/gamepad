package com.indelible.gamepad.ui.screen

import android.content.pm.ActivityInfo
import android.view.WindowManager
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ChangeHistory
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.CropDin
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.indelible.gamepad.angleToUserCoordinate
import com.indelible.gamepad.ui.components.LeftPadDirectionShape
import com.indelible.gamepad.ui.core.JoystickPosition
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun GamePadScreen(){
    val viewModel = remember { PadScreenViewModel() }
    val activity = LocalActivity.current

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 36.dp)
                .requiredHeight(420.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Joystick(updatePosition = {
                viewModel.updateLeftJoystickPosition(it)
            })
            OutlinedButton(onClick = {}) {}
            Joystick(updatePosition = {
                viewModel.updateRightJoystickPosition(it)
            })
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp, start = 36.dp)
        ) {
            Box(
                modifier = Modifier.requiredSize(180.dp)
            ) {
                PadRoundedButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    icon = Icons.Default.Close
                )

                PadRoundedButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    icon = Icons.Outlined.ChangeHistory,
                    rotateIcon = true
                )

                PadRoundedButton(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    icon = Icons.Outlined.Circle
                )

                PadRoundedButton(
                    modifier = Modifier.align(Alignment.TopCenter),
                    icon = Icons.Outlined.CropDin
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 36.dp, start = 36.dp)
        ){
            Box(
                modifier = Modifier.requiredSize(180.dp)
            ) {
                LeftPadDirectionButton(Modifier.align(Alignment.BottomCenter))
                RightPadDirectionButton(Modifier.align(Alignment.TopCenter))

                TopPadDirectionButton(modifier = Modifier.align(Alignment.CenterEnd))
                BottomPadDirectionButton(modifier = Modifier.align(Alignment.CenterStart))
            }
        }

        Surface(
            modifier = Modifier
                .requiredSize(192.dp, 360.dp)
                .padding(end = 20.dp)
                .align(Alignment.CenterEnd),
            border = CardDefaults.outlinedCardBorder(),
            shape = RoundedCornerShape(12.dp)
        ) {  }
    }

    DisposableEffect(Unit) {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            viewModel.closeConnection()
        }
    }
}

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
                .size(150.dp),
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

@Composable
fun PadRoundedButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    rotateIcon: Boolean = false,
    onClick: () -> Unit = {}
){
    Surface(
        onClick = onClick,
        modifier = modifier.size(64.dp),
        shape = CircleShape,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = if (rotateIcon) 90f else 0f),
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}

@Composable
fun LeftPadDirectionButton(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .size(32.dp, 48.dp)
            .drawBehind {
            val width = size.width
            val height = size.height

            val path = Path().apply {
                moveTo(width / 2, 0f)
                lineTo(0f, height/3)
                lineTo(0f, height)
                lineTo(width, height)

                lineTo(width, height/3)
                close()
            }

            drawPath(
                path,
                color = Color.Black,
                style = Stroke(
                    width = 3.dp.toPx(),
                    pathEffect = PathEffect.cornerPathEffect(8.dp.toPx())
                )
            )
        }
    )
}

@Composable
fun RightPadDirectionButton(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .size(36.dp, 54.dp)
            .padding(4.dp)
            .drawBehind {
                val width = size.width
                val height = size.height

                val path = Path().apply {
                    lineTo(0f, height-height/3)

                    lineTo(width/2, height)
                    lineTo(width, height-height/3)

                    lineTo(width,0f)
                    close()
                }
                drawPath(
                    path,
                    color = Color.Black,
                    style = Stroke(
                        width = 3.dp.toPx(),
                        pathEffect = PathEffect.cornerPathEffect(8.dp.toPx())
                    )
                )
            }
    )
}

@Composable
fun TopPadDirectionButton(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .size(48.dp, 32.dp)
            .drawBehind {
                val width = size.width
                val height = size.height

                val path = Path().apply {
                    moveTo(width, 0f)
                    lineTo(width/3, 0f)

                    lineTo(0f, height/2)
                    lineTo(width/3, height)

                    lineTo(width,height)
                    close()
                }
                drawPath(
                    path,
                    color = Color.Black,
                    style = Stroke(
                        width = 3.dp.toPx(),
                        pathEffect = PathEffect.cornerPathEffect(8.dp.toPx())
                    )
                )
            }
    )
}

@Composable
fun BottomPadDirectionButton(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .size(48.dp, 32.dp)
            .drawBehind {
                val width = size.width
                val height = size.height
                val cornerRadius = 20f

                val path = Path().apply {
                    lineTo(width-width/3, 0f)
                    lineTo(width, height/2)
                    lineTo(width-width/3, height)

                    lineTo(0f,height)
                    close()
                }
                drawPath(
                    path,
                    color = Color.Black,
                    style = Stroke(
                        width = 3.dp.toPx(),
                        pathEffect = PathEffect.cornerPathEffect(8.dp.toPx())
                    )
                )
            }
    )
}



@Preview(
    showBackground = true,
    //device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun GamePadScreenPreview(){
    GamePadScreen()
}