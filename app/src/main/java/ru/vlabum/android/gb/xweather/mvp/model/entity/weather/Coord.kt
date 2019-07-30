package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coord {

    @Expose
    @SerializedName("lon")
    var lon: Float = 0f

    @Expose
    @SerializedName("lat")
    var lat: Float = 0f

}