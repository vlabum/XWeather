package ru.vlabum.android.gb.xweather.ui.image

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vlabum.android.gb.xweather.mvp.model.api.INetworkStatus
import ru.vlabum.android.gb.xweather.mvp.model.cache.ImageCache
import ru.vlabum.android.gb.xweather.mvp.model.image.IImageLoader
import timber.log.Timber
import java.io.File
import javax.inject.Inject

open class GlideImageLoader(val mainThreadScheduler: Scheduler) : IImageLoader {

    private lateinit var container: ImageView

    @Inject
    lateinit var networkStatus: INetworkStatus

    @Inject
    lateinit var imageCache: ImageCache

    var isSaved = false

    fun setContainer(container: ImageView) {
        this.container = container
    }

    /*запускать не в основном треде*/
    override fun checkLoaded(url: String) {
        isSaved = false
        imageCache.contains(url)
            .observeOn(mainThreadScheduler)
            .map { isSaved = it }
            .subscribe()
    }

    override fun loadInto(url: String) {

        // если картинка закеширована, не будем ее качать из интернета, иначе - будем
        if (isSaved) {

            val d = imageCache.getImg(url)
                .observeOn(mainThreadScheduler)
                .map {img ->
                    Glide.with(container.context)
                        .load(File(img.getPath()))
                        .into(container)
                    img
                }
                .subscribe()
        } else if (networkStatus.isOnline()) {
            Glide.with(container.context)
                .asBitmap()
                .load(url)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let {
                            imageCache.saveImage(url, it).subscribe()
                        }
                        return false
                    }
                })
                .into(container)
        } else {
            //TODO: сделать оповещение, что иконку невозможно отобразить
        }
    }

}
