package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose

class Wind {

    @Expose
    var speed: Float = 0f

    @Expose
    var deg: Float = 0f

    fun getSpeedStr(): String {
        return speed.toString()
    }

}