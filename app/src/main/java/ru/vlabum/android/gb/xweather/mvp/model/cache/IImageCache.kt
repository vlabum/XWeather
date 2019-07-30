package ru.vlabum.android.gb.xweather.mvp.model.cache

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.vlabum.android.gb.xweather.mvp.model.entity.IImage

interface IImageCache {
    fun findFirst(url: String): Maybe<IImage>
    fun contains(url: String): Single<Boolean>
    fun clear(): Completable
    fun saveImage(url: String, path: String): Completable
}