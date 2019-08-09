package ru.vlabum.android.gb.xweather.di.module

import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import ru.vlabum.android.gb.xweather.mvp.model.repo.IWeatherRepo
import ru.vlabum.android.gb.xweather.mvp.model.repo.storage.IStorage
import ru.vlabum.android.gb.xweather.mvp.model.repo.storage.RoomStorage
import javax.inject.Singleton

@Module
open class TestRepoModule {

    @Singleton
    @Provides
    open fun weatherRepo(): IWeatherRepo {
        return Mockito.mock<IWeatherRepo>(IWeatherRepo::class.java)
    }

    @Singleton
    @Provides
    open fun storage(): IStorage {
        return RoomStorage()
    }

}