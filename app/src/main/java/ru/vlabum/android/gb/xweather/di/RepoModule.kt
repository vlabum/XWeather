package ru.vlabum.android.gb.xweather.di

import dagger.Module
import dagger.Provides
import ru.vlabum.android.gb.xweather.mvp.model.api.IDataSource
import ru.vlabum.android.gb.xweather.mvp.model.api.INetworkStatus
import ru.vlabum.android.gb.xweather.mvp.model.repo.IWeatherRepo
import ru.vlabum.android.gb.xweather.mvp.model.repo.WeatherRepo
import ru.vlabum.android.gb.xweather.mvp.model.repo.storage.IStorage
import ru.vlabum.android.gb.xweather.mvp.model.repo.storage.RoomStorage
import ru.vlabum.android.gb.xweather.mvp.presenter.ICityListPresenter
import ru.vlabum.android.gb.xweather.mvp.presenter.MainPresenter
import javax.inject.Named

@Module(includes = [ApiModule::class, UtilsModule::class])
open class RepoModule {

    @Named("appid")
    @Provides
    open fun appid(): String {
        return "634e19e819dc6d0d194b4b71b82d1e63"
    }

    @Named("lang")
    @Provides
    fun lang(): String {
        return "ru"
    }

    @Named("baseUrlIcon")
    @Provides
    open fun baseUrlRepo(): String {
        return "https://openweathermap.org/img/w/"
    }

    @Provides
    open fun storage(): IStorage {
        return RoomStorage()
    }

    @Provides
    fun weather(
        api: IDataSource,
        networkStatus: INetworkStatus,
        @Named("appid") appid: String,
        @Named("lang") lang: String,
        @Named("baseUrlIcon") baseUrlIcon: String
    ): IWeatherRepo {
        return WeatherRepo(api, networkStatus, appid, lang, baseUrlIcon)
    }

    @Provides
    fun cityListPresenter(): ICityListPresenter {
        return MainPresenter.CityListPresenter()
    }
}