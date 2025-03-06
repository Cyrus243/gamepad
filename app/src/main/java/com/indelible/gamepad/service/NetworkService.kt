package com.indelible.gamepad.service

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream
import java.net.Socket


interface NetworkService {
    suspend fun connect(ipAddress: String, port: Int)
    suspend fun disconnect()
    suspend fun sendData(data: ByteArray)
}

class NetworkServiceImpl: NetworkService {
    private var socket: Socket? = null
    private var outputStream: OutputStream? = null

    override suspend fun connect(ipAddress: String, port: Int) {
        withContext(Dispatchers.IO) {
            socket = Socket(ipAddress, port)
            outputStream = socket?.getOutputStream()
            Log.d(TAG, "connected to the server")
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