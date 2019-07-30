package ru.vlabum.android.gb.xweather.mvp.model.repo

import io.reactivex.Single
import ru.vlabum.android.gb.xweather.mvp.model.entity.ICity
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.CurrentWeather
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.IWeather

interface IWeatherRepo {

    fun getWeather(city: ICity): Single<CurrentWeather>

    fun getUrlIcon(weather: IWeather): String

}