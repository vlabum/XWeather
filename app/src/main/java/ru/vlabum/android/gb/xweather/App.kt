package ru.vlabum.android.gb.xweather

import android.app.Application
import ru.vlabum.android.gb.xweather.di.AppComponent
import ru.vlabum.android.gb.xweather.di.DaggerAppComponent
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.db.Database
import timber.log.Timber

class App : Application() {

    companion object {
        private lateinit var instance: App
        fun getInstance(): App {
            return instance
        }
    }

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())

        Database.create(this)

        appComponent = DaggerAppComponent.builder()
            .build()
    }

    fun getAppComponent() = appComponent

}