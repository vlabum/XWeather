package ru.vlabum.android.gb.xweather.mvp.model.repo

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.vlabum.android.gb.xweather.mvp.model.api.IDataSource
import ru.vlabum.android.gb.xweather.mvp.model.api.INetworkStatus
import ru.vlabum.android.gb.xweather.mvp.model.entity.ICity
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.CurrentWeather
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.IWeather

class WeatherRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val appid: String,
    private val lang: String,
    private val baseUrlIcon: String
) : IWeatherRepo {

    override fun getUrlIcon(weather: IWeather): String {
        return baseUrlIcon + weather.getNameIcon() + ".png"
    }

    override fun getWeather(city: ICity): Single<CurrentWeather> {
        if (networkStatus.isOnline()) {
            return api.getWeather(appid, city.getName(), lang)
                .subscribeOn(Schedulers.io())
        } else {
            //TODO: сделать оповещение об отсутствии сети
            return Single.create { CurrentWeather() }
        }
    }

}