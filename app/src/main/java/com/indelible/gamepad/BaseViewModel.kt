package com.indelible.gamepad

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import com.indelible.gamepad.common.SnackBarManager

open class BaseViewModel(
    val snackBarManager: SnackBarManager = SnackBarManager,
): ViewModel() {}