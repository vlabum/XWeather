package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Main() {

    enum class TypeTemp { CEL, FAR, K }

    enum class TypePressure { PA, HG }

    constructor(temp: Float, pressure: Float, humidity: Float) : this() {
        this.temp = temp
        this.pressure = pressure
        this.humidity = humidity
    }

    @Expose
    @SerializedName("temp")
    var temp: Float = 0f

    @Expose
    @SerializedName("pressure")
    var pressure: Float = 0f

    @Expose
    @SerializedName("humidity")
    var humidity: Float = 0f

    @Expose
    @SerializedName("temp_min")
    var temp_min: Float = 0f

    @Expose
    @SerializedName("temp_max")
    var temp_max: Float = 0f

    @Expose
    @SerializedName("sea_level")
    var sea_level: Float = 0f

    @Expose
    @SerializedName("grnd_level")
    var grnd_level: Float = 0f

    @Expose
    @SerializedName("temp_kf")
    var temp_kf: Float = 0f

    fun getTemp(typeTemp: TypeTemp): Int {
        if (typeTemp == TypeTemp.CEL) return Math.round(temp - 273.15f)
        if (typeTemp == TypeTemp.FAR) return Math.round(((temp - 273.15f) * 9f / 5f) + 32f)
        return Math.round(temp)
    }

    fun getHumidityStr(): String {
        return (humidity.toInt()).toString()
    }

    fun getPressureStr(typePressure: TypePressure): String {
        if (typePressure == TypePressure.PA)
            return (pressure.toInt()).toString()
        if (typePressure == TypePressure.HG)
            return (pressure / 1.333f).toInt().toString()
        return ""
    }

}