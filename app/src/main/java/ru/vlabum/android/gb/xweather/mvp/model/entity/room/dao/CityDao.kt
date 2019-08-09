package ru.vlabum.android.gb.xweather.mvp.model.entity.room.dao

import androidx.room.*
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.RoomCity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(city: RoomCity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg city: RoomCity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(city: List<RoomCity>)

    @Update
    abstract fun update(city: RoomCity)

    @Update
    abstract fun update(vararg city: RoomCity)

    @Update
    abstract fun update(city: List<RoomCity>)

    @Delete
    abstract fun delete(city: RoomCity)

    @Delete
    abstract fun delete(vararg city: RoomCity)

    @Delete
    abstract fun delete(city: List<RoomCity>)

    @Query("SELECT * FROM RoomCity")
    abstract fun getAll(): List<RoomCity>

    @Query("SELECT * FROM RoomCity WHERE name = :name LIMIT 1")
    abstract fun findByName(name: String): RoomCity?

}