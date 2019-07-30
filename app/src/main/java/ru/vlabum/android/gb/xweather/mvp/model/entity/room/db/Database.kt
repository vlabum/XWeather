package ru.vlabum.android.gb.xweather.mvp.model.entity.room.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.RoomCity
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.RoomImage
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.dao.CityDao
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.dao.ImageDao

@androidx.room.Database(entities = [RoomCity::class, RoomImage::class], version = 1)
abstract class Database : RoomDatabase() {
    companion object {

        private val DB_NAME = "weatherRepo.db"

        @Volatile
        private lateinit var instance: Database

        fun create(context: Context) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                Database::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Synchronized
        fun getInstance(): Database {
            if (instance == null) {
                throw RuntimeException("Database has not been created. Please call create()")
            }
            return instance
        }

    }

    abstract fun getCityDao(): CityDao

    abstract fun getImageDao(): ImageDao

}