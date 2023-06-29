package com.example.winfox_test

import android.app.Application
import com.chibatching.kotpref.Kotpref
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class WinFoxApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@WinFoxApplication)
            modules(appModule)
        }

        Kotpref.init(this)
    }
}
