package com.indelible.gamepad

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel() {
    val snackBarHostState = SnackbarHostState()

    init {
        viewModelScope.launch {
            snackBarManager.snackBarMessage.filterNotNull().collect { message ->
                snackBarHostState.showSnackbar(message.message, withDismissAction = true)
                //snackBarManager.clearMessageList()
            }
        }
    }
}