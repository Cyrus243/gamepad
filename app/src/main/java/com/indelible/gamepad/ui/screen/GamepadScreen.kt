package com.indelible.gamepad.ui.screen

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.view.WindowManager
import androidx.activity.compose.LocalActivity
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.indelible.gamepad.ui.components.BottomPadDirectionButton
import com.indelible.gamepad.ui.components.Joystick
import com.indelible.gamepad.ui.components.LeftPadDirectionButton
import com.indelible.gamepad.ui.components.PadRoundedButton
import com.indelible.gamepad.ui.components.RightPadDirectionButton
import com.indelible.gamepad.ui.components.TopPadDirectionButton

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun GamePadScreen(){
    val viewModel = remember { PadScreenViewModel() }
    val activity = LocalActivity.current

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        DirectionButtonGroup()

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
            Spacer(modifier = Modifier.height(16.dp))
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

        RoundActionsButtonGroup()
    }

    DisposableEffect(Unit) {
        val window = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            viewModel.closeConnection()
        }
    }
}

@Composable
fun DirectionButtonGroup(){
    Box(
        modifier = Modifier
            .padding()
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
}

@Composable
fun RoundActionsButtonGroup(){
    Box(modifier = Modifier) {
        Box(
            modifier = Modifier.requiredSize(200.dp)
        ) {
            PadRoundedButton(
                modifier = Modifier.align(Alignment.BottomCenter),
                icon = Icons.Default.Close
            )

            PadRoundedButton(
                modifier = Modifier.align(Alignment.TopCenter),
                icon = Icons.Outlined.ChangeHistory,
            )

            PadRoundedButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                icon = Icons.Outlined.Circle
            )

            PadRoundedButton(
                modifier = Modifier.align(Alignment.CenterStart),
                icon = Icons.Outlined.CropDin
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