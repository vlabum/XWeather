package ru.vlabum.android.gb.xweather

import android.app.Application
import timber.log.Timber

class App : Application() {

    companion object {
        private lateinit var instance: App
        fun getInstance(): App {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
    }
}