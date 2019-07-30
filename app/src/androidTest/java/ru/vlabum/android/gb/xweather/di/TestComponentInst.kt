package ru.vlabum.android.gb.xweather.di

import dagger.Component
import ru.vlabum.android.gb.xweather.WeatherInstrumentedTest
import javax.inject.Singleton

@Singleton
@Component(modules = [RepoModule::class, UtilsModule::class, ApiModule::class, CacheModule::class])
interface TestComponentInst {
    fun inject(userRepoInstrumentedTest: WeatherInstrumentedTest)
}