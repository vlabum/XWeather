package ru.vlabum.android.gb.xweather.mvp.model.image

interface IImageLoader {
    fun loadInto(url: String)
    fun checkLoaded(url: String)
}