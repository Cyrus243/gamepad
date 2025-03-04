package com.indelible.gamepad.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indelible.gamepad.service.NetworkServiceImpl
import com.indelible.gamepad.ui.core.JoystickPosition
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PadScreenViewModel(
    private val networkService: NetworkServiceImpl = NetworkServiceImpl()
): ViewModel() {
    private val _uiState = MutableStateFlow(PadScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                networkService.connect()
            }catch (e: Exception){
                Log.e(TAG, "error connecting to the server", e)
            }
        }
    }

    fun updateLeftJoystickPosition(position: JoystickPosition){
        _uiState.update {
            uiState.value.copy(leftJoystickPosition = position)
        }
        onStateChange(byteArrayOf(255.toByte(), 1, 2, 1, 1, 0, position.toEncodedByte(), 0))
    }

    fun updateRightJoystickPosition(position: JoystickPosition) {
        _uiState.update {
            uiState.value.copy(rightJoystickPosition = position)
        }
        onStateChange(byteArrayOf(255.toByte(), 1, 2, 1, 1, 0, position.toEncodedByte(), 0))
    }

    fun updatePressedButtonsIndex(index: Int) {
        _uiState.update {
            uiState.value.copy(pressedButtonsIndex = index)
        }
        onStateChange(byteArrayOf(255.toByte(), 1, 2, 1, 1, index.toByte(), 0, 0))
    }

    fun updatePressedDirectionIndex(index: Int) {
        _uiState.update {
            uiState.value.copy(pressedDirectionIndex = index)
        }
        onStateChange(byteArrayOf(255.toByte(), 1, 2, 1, 1, index.toByte(), 0, 0))
    }

    fun closeConnection(){
        viewModelScope.launch {
            networkService.disconnect()
        }
    }


    private fun onStateChange(data: ByteArray){
        viewModelScope.launch {
            try {
                async { networkService.sendData(data) }.await()
            }catch (e: Exception){
                Log.e(TAG, "error sending data", e)
                networkService.disconnect()
            }
        }
    }
}

data class PadScreenState(
    val leftJoystickPosition: JoystickPosition = JoystickPosition(),
    val rightJoystickPosition: JoystickPosition = JoystickPosition(),
    val pressedButtonsIndex: Int = 0,
    val pressedDirectionIndex: Int = 0
)

private const val TAG = "PadScreenViewModel"