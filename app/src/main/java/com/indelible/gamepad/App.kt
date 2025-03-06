package com.indelible.gamepad

import android.app.Application
import com.indelible.gamepad.di.commonModule
import com.indelible.gamepad.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(commonModule, viewmodelModule)
        }
    }
}