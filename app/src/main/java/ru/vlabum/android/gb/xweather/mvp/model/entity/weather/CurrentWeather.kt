package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose

class CurrentWeather : OpenWeatherMap(), IWeather {

    override fun getTemp(): Int {
        return main.getTemp(Main.TypeTemp.CEL)
    }

    override fun getCity(): String {
        return name
    }

    override fun getDescription(): String {
        return weather[0].description
    }

    override fun getSpeedWind(): Float {
        return wind.speed
    }

    override fun getHumidity(): Float {
        return main.humidity
    }

    override fun getPressure(): Float {
        return main.pressure
    }

    override fun getNameIcon(): String {
        return this.weather[0].icon
    }

    @Expose
    lateinit var coord: Coord

    @Expose
    lateinit var weather: List<Weather>

    @Expose
    lateinit var base: String

    @Expose
    lateinit var main: Main

    @Expose
    var visibility: Float = 0f

    @Expose
    lateinit var wind: Wind

    @Expose
    lateinit var clouds: Clouds

    @Expose
    var rain: Rain? = null

    @Expose
    var snow: Snow? = null

    @Expose
    var dt: Int = 0 //  Time of data calculation, unix, UTC

    @Expose
    lateinit var sys: Sys

    @Expose
    var id: Int = 0

    @Expose
    lateinit var name: String

}