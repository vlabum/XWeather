package ru.vlabum.android.gb.xweather.di

import dagger.Module
import dagger.Provides
import ru.vlabum.android.gb.xweather.mvp.model.api.INetworkStatus
import ru.vlabum.android.gb.xweather.ui.NetworkStatus
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun networkStatus(): INetworkStatus {
        return NetworkStatus()
    }
}
