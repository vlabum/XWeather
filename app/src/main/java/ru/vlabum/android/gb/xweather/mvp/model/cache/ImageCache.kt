package ru.vlabum.android.gb.xweather.mvp.model.cache

import android.graphics.Bitmap
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.wait
import ru.vlabum.android.gb.xweather.App
import ru.vlabum.android.gb.xweather.mvp.model.entity.IImage
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class ImageCache(val cache: IImageCache, val mainThreadScheduler: Scheduler) {

    companion object {
        private val IMAGE_FOLDER_NAME = "image"
    }

    fun getFile(url: String): File? {
        var file: File? = null
        val imgObservable = cache.findFirst(url)
        val d = imgObservable.observeOn(mainThreadScheduler)
            .subscribe({ img -> file = File(img.getPath()) })
        return file
    }

    fun getImg(url: String): Maybe<IImage> {
        return cache.findFirst(url)
//        var file: File? = null
//        val imgObservable = cache.findFirst(url)
//        val d = imgObservable.observeOn(mainThreadScheduler)
//            .subscribe({ img -> file = File(img.getPath()) })
//        return file
    }

    fun contains(url: String): Single<Boolean> {
        return cache.contains(url)

//        var result = false
//        val d = cache.contains(url)
//            .subscribeOn(Schedulers.io())
//            .map {
//                result = it
//                it
//            }
//            .observeOn(mainThreadScheduler)
//            .subscribe()
//        return result
    }

    fun clear(): Completable {
        return Completable.fromAction {
            cache.clear()
            deleteFileOrDirRecursive(getImageDir())
        }
    }

    fun saveImage(url: String, bitmap: Bitmap): Completable {

        return Completable.fromAction {

            if (!getImageDir().exists() && !getImageDir().mkdirs()) {
                throw RuntimeException("Failed to create directory: " + getImageDir().toString())
            }

            val fileFormat: String = if (url.contains(".jpg")) ".jpg" else ".png"
            val imageFile: File = File(getImageDir(), SHA1(url) + fileFormat)
            val fos: FileOutputStream

            try {
                fos = FileOutputStream(imageFile)
                bitmap.compress(
                    if (fileFormat == "jpg") Bitmap.CompressFormat.JPEG else Bitmap.CompressFormat.PNG,
                    100,
                    fos
                )
                fos.close()
            } catch (e: Exception) {
                Timber.d("Failed to save image")
            }

            cache.saveImage(url, imageFile.path).subscribeOn(Schedulers.io()).subscribe()
        }
    }

    fun SHA1(s: String): String {
        var m: MessageDigest? = null

        try {
            m = MessageDigest.getInstance("SHA1")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        m!!.update(s.toByteArray(), 0, s.length)
        return BigInteger(1, m.digest()).toString(16)
    }

    fun getImageDir(): File {
        val path: String = App.getInstance().getExternalFilesDir(null)!!.path + "/" + IMAGE_FOLDER_NAME
        return File(path)
    }

    fun deleteFileOrDirRecursive(fileOrDirectory: File): Completable {
        return Completable.fromAction {
            if (fileOrDirectory.isDirectory) {
                for (child in fileOrDirectory.listFiles()) {
                    deleteFileOrDirRecursive(child)
                }
            }
            fileOrDirectory.delete()
        }
    }
}