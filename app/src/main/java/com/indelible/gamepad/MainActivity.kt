package com.indelible.gamepad

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.indelible.gamepad.ui.screen.GamePadScreen
import com.indelible.gamepad.ui.theme.GamepadTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel by viewModel()

            GamepadTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(
                            hostState = viewModel.snackBarHostState,
                            modifier = Modifier.padding(8.dp),
                            snackbar = { snackBarData ->
                                Snackbar(
                                    snackbarData = snackBarData,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    Surface(modifier = Modifier.padding(innerPadding)) {
                        GamePadScreen()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onStart() {
        super.onStart()
        this.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}
