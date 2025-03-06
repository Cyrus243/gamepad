package com.indelible.gamepad

import androidx.lifecycle.ViewModel
import com.indelible.gamepad.common.SnackBarManager
import org.koin.core.component.KoinComponent

open class BaseViewModel(
    val snackBarManager: SnackBarManager = SnackBarManager,
): ViewModel(), KoinComponent {}