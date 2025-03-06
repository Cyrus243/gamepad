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


interface NetworkService {
    suspend fun connect(ipAddress: String, port: Int): Result<ConnectionState, DataError.Network>
    suspend fun disconnect()
    suspend fun sendData(data: ByteArray)
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

    override suspend fun sendData(data: ByteArray) {
        withContext(Dispatchers.IO){
            outputStream?.write(data.size)
            outputStream?.flush()

            outputStream?.write(data)
            outputStream?.flush()
        }
    }

}

private const val TAG = "NetworkService"