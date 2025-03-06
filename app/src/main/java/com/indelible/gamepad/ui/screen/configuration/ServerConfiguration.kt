package com.indelible.gamepad.ui.screen.configuration

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.indelible.gamepad.ui.components.CustomTextField
import com.indelible.gamepad.ui.core.ConnectionState
import com.indelible.gamepad.ui.core.ConnectionType

@Composable
fun ColumnScope.ServerConfiguration(
    connectionState: ConnectionState,
    onConnectClick: () -> Unit,
    onDisconnectClick: () -> Unit,
    selectedConnectionType: ConnectionType,
    onSelectedConnectionTypeChange: (ConnectionType) -> Unit,
    ipAddress: String,
    port: String,
    onIpAddressChange: (String) -> Unit,
    onPortChange: (String) -> Unit
){

    ConnectionModeSelector(
        selectedConnectionType = selectedConnectionType,
        onSelectedConnectionTypeChange = onSelectedConnectionTypeChange
    )

    Text(
        text = "IP Address",
        style = MaterialTheme.typography.titleSmall
    )
    Spacer(modifier = Modifier.height(4.dp))
    CustomTextField(
        value = ipAddress,
        onValueChange = { onIpAddressChange(it) },
        placeHolder = "IP Address",
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = "Port",
        style = MaterialTheme.typography.titleSmall
    )
    Spacer(modifier = Modifier.height(4.dp))
    CustomTextField(
        value = port,
        onValueChange = { onPortChange(it) },
        placeHolder = "Port",
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(modifier = Modifier.height(8.dp))

    FilledTonalButton(
        modifier = Modifier.align(Alignment.End),
        onClick = {
            if (connectionState == ConnectionState.CONNECTED)
                onDisconnectClick() else onConnectClick()
        },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = if (connectionState == ConnectionState.CONNECTED)
                MaterialTheme.colorScheme.error else Color.Unspecified
        )
    ) {
        if (connectionState == ConnectionState.CONNECTED)
            Text(
                text = "Disconnect",
                style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onError)
            )
        else
            Text(text = "Connect")
    }

}

@Composable
fun ConnectionModeSelector(
    selectedConnectionType: ConnectionType,
    onSelectedConnectionTypeChange: (ConnectionType) -> Unit
){
    Row(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedConnectionType == ConnectionType.Server,
                onClick = { onSelectedConnectionTypeChange(ConnectionType.Server) }
            )
            Text(
                text = "TCP server",
                style = MaterialTheme.typography.titleSmall
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            RadioButton(
                selected = selectedConnectionType == ConnectionType.BLE,
                onClick = { onSelectedConnectionTypeChange(ConnectionType.BLE) }
            )
            Text(
                text = "BLE server",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}