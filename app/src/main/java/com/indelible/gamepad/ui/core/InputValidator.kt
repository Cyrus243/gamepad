package com.indelible.gamepad.ui.core

import java.util.regex.Pattern

class InputValidator{

    fun validateIpAddress(ipAddress: String): Boolean {
        return ipv4Pattern.matcher(ipAddress).matches()
    }

    companion object{
        private val ipv4Pattern = Pattern.compile(
            ("^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\\."
                    + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\\."
                    + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\\."
                    + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$")
        )
    }
}