package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose

class Weather {

    @Expose
    var id: Int = 0

    @Expose
    lateinit var main: String

    @Expose
    lateinit var description: String

    @Expose
    lateinit var icon: String

}