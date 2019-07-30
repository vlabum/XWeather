package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class OpenWeatherMap {

    @Expose
    @SerializedName("cod")
    var cod: Int = 0

}