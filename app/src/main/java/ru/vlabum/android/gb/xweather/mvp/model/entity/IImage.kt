package ru.vlabum.android.gb.xweather.mvp.model.entity

interface IImage {
    fun getPath(): String
    fun setPath(path: String)
    fun setUrl(url: String)
}