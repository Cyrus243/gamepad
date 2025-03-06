package com.indelible.gamepad.service

import android.util.Log
import com.indelible.gamepad.ui.core.ConnectionState
import com.indelible.gamepad.ui.core.DataError
import com.indelible.gamepad.ui.core.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.net.NoRouteToHostException
import java.net.Socket
import java.net.SocketException


interface NetworkService {
    suspend fun connect(ipAddress: String, port: Int): Result<ConnectionState, DataError.Network>
    suspend fun disconnect()
    suspend fun sendData(data: ByteArray): Result<String, DataError.Network>
}

class NetworkServiceImpl: NetworkService {
    private var socket: Socket? = null
    private var outputStream: OutputStream? = null

    override suspend fun connect(ipAddress: String, port: Int): Result<ConnectionState, DataError.Network>{
        return withContext(Dispatchers.IO) {
            try {
                socket = Socket(ipAddress, port)
                outputStream = socket?.getOutputStream()
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

    override suspend fun sendData(data: ByteArray): Result<String, DataError.Network> {
        return withContext(Dispatchers.IO){
            try {
                outputStream?.write(data.size)
                outputStream?.flush()

                outputStream?.write(data)
                outputStream?.flush()
                Result.Success("Data sent")
            }catch (e: SocketException){
                Log.e(TAG, "sendData: ${e.message}")
                Result.Error(DataError.Network.SOCKET_CLOSED)
            }
        }
    }

}

private const val TAG = "NetworkService"