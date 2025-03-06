package com.indelible.gamepad.di

import com.indelible.gamepad.common.AppPreferences
import com.indelible.gamepad.service.NetworkService
import com.indelible.gamepad.service.NetworkServiceImpl
import com.indelible.gamepad.ui.core.InputValidator
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    single { AppPreferences(androidContext()) }
    singleOf(::InputValidator)
    single<NetworkService> { NetworkServiceImpl() }
}