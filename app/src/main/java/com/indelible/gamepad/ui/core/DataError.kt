package com.indelible.gamepad.ui.core

sealed interface DataError: Error {
    enum class Network : DataError {
        HOST_UNREACHABLE,
        NETWORK_ERROR,
        UNKNOWN
    }
}