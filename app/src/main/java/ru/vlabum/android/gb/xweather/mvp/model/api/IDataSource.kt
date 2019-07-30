package ru.vlabum.android.gb.xweather.mvp.model.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.CurrentWeather

interface IDataSource {

    @GET("/data/2.5/weather")
    fun getWeather(
        @Query("APPID") appid: String,
        @Query("q") cityName: String,
        @Query("lang") lang: String
    ): Single<CurrentWeather>

//    @GET
//    fun getUserRepos(@Url url: String): Single<List<Repository>>
}