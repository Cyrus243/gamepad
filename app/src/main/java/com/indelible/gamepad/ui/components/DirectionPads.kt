package com.indelible.gamepad.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

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