package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Clouds {

    @Expose
    @SerializedName("all")
    var all: Float = 0f

}