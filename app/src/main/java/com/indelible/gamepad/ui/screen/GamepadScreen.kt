package com.indelible.gamepad.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.ChangeHistory
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.CropDin
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.indelible.gamepad.BOTTOM_DIRECTION
import com.indelible.gamepad.CIRCLE_BUTTON
import com.indelible.gamepad.LEFT_DIRECTION
import com.indelible.gamepad.RIGHT_DIRECTION
import com.indelible.gamepad.SQUARE_BUTTON
import com.indelible.gamepad.TOP_DIRECTION
import com.indelible.gamepad.TRIANGLE_BUTTON
import com.indelible.gamepad.X_BUTTON
import com.indelible.gamepad.ui.components.BottomPadDirectionButton
import com.indelible.gamepad.ui.components.Joystick
import com.indelible.gamepad.ui.components.LeftPadDirectionButton
import com.indelible.gamepad.ui.components.PadRoundedButton
import com.indelible.gamepad.ui.components.RightPadDirectionButton
import com.indelible.gamepad.ui.components.TopPadDirectionButton

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun GamePadScreen(){
    val viewModel: PadScreenViewModel = viewModel()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        DirectionButtonGroup(){ command ->
            viewModel.updatePressedDirectionIndex(command)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                border = CardDefaults.outlinedCardBorder(),
                shape = RoundedCornerShape(12.dp)
            ) {  }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "CONNECTED",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Green)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Joystick(updatePosition = {
                    viewModel.updateLeftJoystickPosition(it)
                })
                OutlinedButton(onClick = {}) {}
                Joystick(updatePosition = {
                    viewModel.updateRightJoystickPosition(it)
                })
            }
        }

        RoundActionsButtonGroup(){
            viewModel.updatePressedButtonsIndex(it)
        }
    }
}

@Composable
fun DirectionButtonGroup(
    onDirectionClick: (command: Int) -> Unit
){
    Box(
        modifier = Modifier
            .padding()
    ){
        Box(
            modifier = Modifier.requiredSize(180.dp)
        ) {
            LeftPadDirectionButton(
                Modifier.align(Alignment.BottomCenter)
                    .clickable { onDirectionClick(LEFT_DIRECTION) }
            )
            RightPadDirectionButton(
                Modifier.align(Alignment.TopCenter)
                    .clickable { onDirectionClick(RIGHT_DIRECTION) }
            )
            TopPadDirectionButton(
                Modifier.align(Alignment.CenterEnd)
                    .clickable { onDirectionClick(TOP_DIRECTION) }
            )
            BottomPadDirectionButton(
                Modifier.align(Alignment.CenterStart)
                    .clickable { onDirectionClick(BOTTOM_DIRECTION) }
            )
        }
    }
}

@Composable
fun RoundActionsButtonGroup(
    onButtonClick: (command: Int) -> Unit
){
    Box(modifier = Modifier) {
        Box(
            modifier = Modifier.requiredSize(200.dp)
        ) {
            PadRoundedButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                icon = Icons.Default.Close,
                onClick = { onButtonClick(X_BUTTON) }
            )

            PadRoundedButton(
                modifier = Modifier.align(Alignment.TopCenter),
                icon = Icons.Outlined.ChangeHistory,
                onClick = { onButtonClick(TRIANGLE_BUTTON) }
            )

            PadRoundedButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                icon = Icons.Outlined.Circle,
                onClick = { onButtonClick(CIRCLE_BUTTON) }
            )

            PadRoundedButton(
                modifier = Modifier.align(Alignment.CenterStart),
                icon = Icons.Outlined.CropDin,
                onClick = { onButtonClick(SQUARE_BUTTON) }
            )
        }
    }
}



@Preview(
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
fun GamePadScreenPreview(){
    GamePadScreen()
}