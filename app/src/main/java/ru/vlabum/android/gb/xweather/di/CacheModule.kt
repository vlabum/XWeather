package ru.vlabum.android.gb.xweather.di

import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.vlabum.android.gb.xweather.mvp.model.cache.IImageCache
import ru.vlabum.android.gb.xweather.mvp.model.cache.ImageCache
import ru.vlabum.android.gb.xweather.mvp.model.cache.RoomImageCache
import javax.inject.Named
import javax.inject.Singleton

@Module
open class CacheModule {

    @Singleton
    @Provides
    open fun imageCache(@Named("imageRoom") imageCache: IImageCache): ImageCache {
        return ImageCache(imageCache, AndroidSchedulers.mainThread())
    }

    @Named("imageRoom")
    @Singleton
    @Provides
    fun roomImageCache(): IImageCache {
        return RoomImageCache()
    }

}