package com.indelible.gamepad.di

import com.indelible.gamepad.MainViewModel
import com.indelible.gamepad.ui.screen.PadScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewmodelModule = module {
    viewModel { PadScreenViewModel(get(), get(), get()) }
    viewModel { MainViewModel() }
}