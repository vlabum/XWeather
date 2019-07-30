package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

interface IWeather {

    fun getNameIcon(): String

    fun getTemp(): Int

    fun getCity(): String

    fun getDescription(): String

    fun getSpeedWind(): Float

    fun getHumidity(): Float

    fun getPressure(): Float

}