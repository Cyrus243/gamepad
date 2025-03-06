package com.indelible.gamepad.service

import android.util.Log
import com.indelible.gamepad.QUIT_MESSAGE
import com.indelible.gamepad.ui.core.ConnectionState
import com.indelible.gamepad.ui.core.DataError
import com.indelible.gamepad.ui.core.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.net.NoRouteToHostException
import java.net.Socket
import java.net.SocketException


interface NetworkService {
    suspend fun connect(ipAddress: String, port: Int): Result<ConnectionState, DataError.Network>
    suspend fun disconnect()
    suspend fun sendData(data: ByteArray): Result<Unit, DataError.Network>
    suspend fun receiveMessage()
}

class NetworkServiceImpl: NetworkService {
    private var socket: Socket? = null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null
    val message = MutableStateFlow<String?>(null)
    private var isRunning = false

    override suspend fun connect(ipAddress: String, port: Int): Result<ConnectionState, DataError.Network>{
        return withContext(Dispatchers.IO) {
            try {
                socket = Socket(ipAddress, port)
                outputStream = socket?.getOutputStream()
                inputStream = socket?.getInputStream()
                isRunning = true

               Result.Success(ConnectionState.CONNECTED)
            }catch (e: NoRouteToHostException){
               Result.Error(DataError.Network.HOST_UNREACHABLE)
            }catch (e: Exception){
                Log.d(TAG, "connect: An error occurred: ${e.cause}")
                Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }


    override suspend fun disconnect() {
        withContext(Dispatchers.IO){
            outputStream?.close()
            socket?.close()
            Log.d(TAG, "disconnected from the server")
        }
    }

    override suspend fun sendData(data: ByteArray): Result<Unit, DataError.Network> {
        return withContext(Dispatchers.IO){
            try {
                outputStream?.write(data.size)
                outputStream?.flush()

                outputStream?.write(data)
                outputStream?.flush()
                Result.Success(Unit)
            }catch (e: SocketException){
                Log.e(TAG, "sendData: ${e.message}")
                Result.Error(DataError.Network.SOCKET_CLOSED)
            }
        }
    }

    override suspend fun receiveMessage() {
        withContext(Dispatchers.IO){
            while (isRunning){
                try {
                    val msg = inputStream?.bufferedReader()?.readLine() ?: continue
                    message.value = msg
                    if (msg == QUIT_MESSAGE)
                        isRunning = false
                }catch (e: Exception){
                    Log.e(TAG, "receiveMessage: ${e.message}")
                }
            }
        }
    }

    fun clearMessage(){
        message.value = null
    }

}

private const val TAG = "NetworkService"