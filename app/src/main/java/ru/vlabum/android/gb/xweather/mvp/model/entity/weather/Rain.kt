package ru.vlabum.android.gb.xweather.mvp.model.entity.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rain {

    @Expose
    @SerializedName("1h")
    var _1h: Int = 0

    @Expose
    @SerializedName("3h")
    var _3h: Int = 0

}