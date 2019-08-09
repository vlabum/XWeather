package ru.vlabum.android.gb.xweather.mvp.model.cache

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import ru.vlabum.android.gb.xweather.mvp.model.entity.IImage
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.RoomImage
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.db.Database

class RoomImageCache : IImageCache {

    override fun findFirst(url: String): Maybe<IImage> {
        return Maybe.create<IImage> {emitter ->
            val result = Database.getInstance().getImageDao().findByUrl(url)
            emitter.onSuccess(result)
        }
            .subscribeOn(Schedulers.io())
    }

    override fun contains(url: String): Single<Boolean> {
        return Single.create<Boolean> { emitter ->
            val img = Database.getInstance().getImageDao().findByUrl(url)
            val result = if (img != null) url == img.url else false
            emitter.onSuccess(result)
        }
            .subscribeOn(Schedulers.io())
    }

    override fun clear(): Completable {
        return Completable.fromAction { Database.getInstance().getImageDao().clear() }
    }

    override fun saveImage(url: String, path: String): Completable {
        return Completable.fromAction {
                var roomImage: RoomImage? = Database.getInstance().getImageDao()
                    .findByUrl(url)
                if (roomImage == null) {
                    roomImage = RoomImage()
                    roomImage.setUrl(url)
                    roomImage.setPath(path)
                }

                Database.getInstance().getImageDao().insert(roomImage)
        }
    }
}