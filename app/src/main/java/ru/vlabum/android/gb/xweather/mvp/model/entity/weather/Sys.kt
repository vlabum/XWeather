package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose

class Sys {

    @Expose
    var type: Int = 0

    @Expose
    var id: Int = 0

    @Expose
    var message: Float = 0f

    @Expose
    lateinit var country: String

    @Expose
    var sunrise: Int = 0

    @Expose
    var sunset: Int = 0

}