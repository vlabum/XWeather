package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City {

    @Expose
    @SerializedName("id")
    var id: Int = 0

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("coord")
    var coord: Coord? = null

    @Expose
    @SerializedName("country")
    var country: String? = null

}