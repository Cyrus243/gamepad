package com.indelible.gamepad.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class SnackBarMessage {
    class StringSnackBar(val message: String): SnackBarMessage()
    companion object{
        fun Throwable.toSnackBarMessage(): StringSnackBar{
            val message = this.message.orEmpty()
            return  StringSnackBar(message)
        }
    }
}

object SnackBarManager {
    private val messages: MutableStateFlow<SnackBarMessage.StringSnackBar?> = MutableStateFlow(null)
    val snackBarMessage: StateFlow<SnackBarMessage.StringSnackBar?>
        get() = messages.asStateFlow()

    fun clearMessageList(){
        messages.value = null
    }
    fun showMessage(message: SnackBarMessage.StringSnackBar){
        messages.value = message
    }
}

object LoadingManager{
    private val _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean?>
        get() = _loadingState.asStateFlow()

    fun changeState(state: Boolean){
        _loadingState.value = state
    }
}