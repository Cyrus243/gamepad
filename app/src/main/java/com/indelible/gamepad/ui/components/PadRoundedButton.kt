package com.indelible.gamepad.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun PadRoundedButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    rotateIcon: Boolean = false,
    onButtonClick: (index: Int, command: Boolean) -> Unit,
    buttonPosition: Int
){
    Surface(
        modifier = modifier
            .size(72.dp)
            .pointerInput(Unit){
                detectTapGestures(
                    onPress = {
                        onButtonClick(buttonPosition, true)
                        tryAwaitRelease()
                        onButtonClick(buttonPosition, false)
                    }
                )
            },
        shape = CircleShape,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = if (rotateIcon) 90f else 0f),
                imageVector = icon,
                contentDescription = null
            )
        }
    }
}