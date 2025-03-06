package com.indelible.gamepad.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indelible.gamepad.BaseViewModel
import com.indelible.gamepad.common.SnackBarMessage
import com.indelible.gamepad.service.NetworkServiceImpl
import com.indelible.gamepad.ui.core.ConnectionState
import com.indelible.gamepad.ui.core.ConnectionType
import com.indelible.gamepad.ui.core.ControllerType
import com.indelible.gamepad.ui.core.DataError
import com.indelible.gamepad.ui.core.JoystickPosition
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.indelible.gamepad.ui.core.Result
import kotlinx.coroutines.delay

class PadScreenViewModel(
    private val networkService: NetworkServiceImpl = NetworkServiceImpl()
): BaseViewModel() {
    private val _uiState = MutableStateFlow(PadScreenState())
    val uiState = _uiState.asStateFlow()

    fun updateLeftJoystickPosition(position: JoystickPosition){
        _uiState.update {
            uiState.value.copy(leftJoystickPosition = position)
        }
        onStateChange(byteArrayOf(255.toByte(), 1, 2, 3, 0, 0, position.toEncodedByte(), 0))
    }

    fun updateRightJoystickPosition(position: JoystickPosition) {
        _uiState.update {
            uiState.value.copy(rightJoystickPosition = position)
        }
        onStateChange(byteArrayOf(255.toByte(), 1, 2, 2, 0, 0, position.toEncodedByte(), 0))
    }

    fun updatePressedButtonsIndex(index: Int) {
        _uiState.update {
            uiState.value.copy(pressedButtonsIndex = index)
        }
        onStateChange(byteArrayOf(255.toByte(), 1, 2, 1, 0, index.toByte(), 0, 0))
    }

    fun updatePressedDirectionIndex(index: Int) {
        _uiState.update {
            uiState.value.copy(pressedDirectionIndex = index)
        }
        onStateChange(byteArrayOf(255.toByte(), 1, 2, 1, 0, index.toByte(), 0, 0))
    }

    fun updateConnectionType(type: ConnectionType){
        _uiState.update {
            uiState.value.copy(connectionType = type)
        }
    }
    fun updateIpAddress(ipAddress: String){
        _uiState.update {
            uiState.value.copy(ipAddress = ipAddress)
        }
    }
    fun updatePort(port: String){
        _uiState.update {
            uiState.value.copy(port = port)
        }
    }

    private fun updateConnectionState(state: ConnectionState){
        _uiState.update {
            uiState.value.copy(connectionState = state)
        }
    }

    fun updateControllerType(type: ControllerType){
        _uiState.update {
            uiState.value.copy(controllerType = type)
        }
    }

    fun closeConnection(){
        viewModelScope.launch {
            async { networkService.disconnect() }.await()
            updateConnectionState(ConnectionState.DISCONNECTED)
        }
    }

    fun connectToServer(onComplete: () -> Unit){
        val ipAddress = uiState.value.ipAddress
        val port = uiState.value.port.toInt()

        viewModelScope.launch {
            updateConnectionState(ConnectionState.CONNECTING)
            delay(300)
            when(val result = async { networkService.connect(ipAddress, port) }.await()){
                is Result.Success -> {
                    updateConnectionState(result.data)
                    onComplete()
                }
                is Result.Error -> {
                    when (result.error){
                        DataError.Network.HOST_UNREACHABLE -> {
                            updateConnectionState(ConnectionState.DISCONNECTED)
                            snackBarManager.showMessage(
                                SnackBarMessage.StringSnackBar("The host server is unreachable!")
                            )
                            Log.d(TAG, "connectToServer: Host unreachable !")
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun onStateChange(data: ByteArray){
        if (uiState.value.connectionState != ConnectionState.CONNECTED)
            return

        viewModelScope.launch {
            when(val result = async { networkService.sendData(data) }.await()){
                is Result.Error -> {
                    when(result.error){
                        DataError.Network.SOCKET_CLOSED -> {
                            updateConnectionState(ConnectionState.DISCONNECTED)
                            networkService.disconnect()
                        }
                        else ->{}
                    }
                }
                else -> {}
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
        closeConnection()
    }
}

data class PadScreenState(
    val leftJoystickPosition: JoystickPosition = JoystickPosition(),
    val rightJoystickPosition: JoystickPosition = JoystickPosition(),
    val pressedButtonsIndex: Int = 0,
    val pressedDirectionIndex: Int = 0,
    val connectionType: ConnectionType = ConnectionType.Server,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val ipAddress: String = "192.168.2.21",
    val port: String = "3000",
    val controllerType: ControllerType = ControllerType.DIGITAL
)

private const val TAG = "PadScreenViewModel"