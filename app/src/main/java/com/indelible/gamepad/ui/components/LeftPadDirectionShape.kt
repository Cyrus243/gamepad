package com.indelible.gamepad.ui.components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.max


class LeftPadDirectionShape(private var matrix: Matrix = Matrix()): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val height = size.height
        val width = size.width
        matrix.reset()

        val path = Path().apply {
            lineTo(0f, height-height/3)

            lineTo(width/2, height)
            lineTo(width, height-height/3)

            lineTo(width,0f)
            close()
        }

        val bounds = path.getBounds()
        val maxDimension = max(bounds.width, bounds.height)
        matrix.scale(size.width / maxDimension, size.height / maxDimension)
        matrix.translate(-bounds.left, -bounds.top)

        path.transform(matrix)
        return Outline.Generic(path)
    }

}