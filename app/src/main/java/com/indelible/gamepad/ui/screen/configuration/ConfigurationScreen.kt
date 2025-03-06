package com.indelible.gamepad.ui.screen.configuration

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.indelible.gamepad.ui.core.ConnectionState
import com.indelible.gamepad.ui.core.ConnectionType
import com.indelible.gamepad.ui.core.ControllerType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ConfigurationSheet(
    connectionState: ConnectionState,
    selectedConnectionType: ConnectionType,
    onSelectedConnectionTypeChange: (ConnectionType) -> Unit,
    onConnectClick: () -> Unit,
    onDisconnectClick: () -> Unit,
    ipAddress: String,
    port: String,
    onIpAddressChange: (String) -> Unit,
    onPortChange: (String) -> Unit,
    selectedControllerType: ControllerType,
    onSelectedControllerTypeChange: (ControllerType) -> Unit
){
    val tabLists by remember { mutableStateOf(listOf("server", "controller")) }
    val pagerState = rememberPagerState{ tabLists.size }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SecondaryTabRow(
                modifier = Modifier
                    .border(.2.dp, Color.Black.copy(alpha = .3f), RoundedCornerShape(8.dp)),
                selectedTabIndex = selectedTabIndex,
                divider = {},
                indicator = {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(selectedTabIndex, matchContentSize = false)
                            .padding(2.dp)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(6.dp))
                            .zIndex(-1f)
                            .background(MaterialTheme.colorScheme.secondaryContainer)

                    )
                }
            ) {
                tabLists.forEachIndexed { index, title ->
                    val interactionSource = remember { MutableInteractionSource() }
                    Tab(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(6.dp)),
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title) },
                        interactionSource = interactionSource
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth(),
                userScrollEnabled = false
            ) { index ->
                when(index) {
                    0 -> {
                        Column(modifier = Modifier) {
                            ServerConfiguration(
                                connectionState = connectionState,
                                onConnectClick = onConnectClick,
                                onDisconnectClick = onDisconnectClick,
                                selectedConnectionType = selectedConnectionType,
                                onSelectedConnectionTypeChange = onSelectedConnectionTypeChange,
                                ipAddress = ipAddress,
                                port = port,
                                onIpAddressChange = onIpAddressChange,
                                onPortChange = onPortChange
                            )
                        }
                    }
                    1 -> {
                        ControllerConfiguration(
                            selectedControllerType = selectedControllerType,
                            onSelectedControllerTypeChange = onSelectedControllerTypeChange
                        )
                    }
                }
            }
        }
    }
}
