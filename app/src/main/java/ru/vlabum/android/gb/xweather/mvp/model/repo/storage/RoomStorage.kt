package ru.vlabum.android.gb.xweather.mvp.model.repo.storage

import io.reactivex.Completable
import io.reactivex.Single
import ru.vlabum.android.gb.xweather.mvp.model.entity.ICity
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.RoomCity
import ru.vlabum.android.gb.xweather.mvp.model.entity.room.db.Database

class RoomStorage : IStorage {

    override fun getCities(): Single<List<ICity>> {
        return Single.create { emitter ->
            val cities = Database.getInstance().getCityDao().getAll()
            emitter.onSuccess(cities)
        }
    }

    override fun addCity(city: ICity): Completable {
        return Completable.fromAction {
            var roomCity: RoomCity? = Database.getInstance().getCityDao().findByName(city.getName())
            if (roomCity == null) {
                roomCity = RoomCity()
                roomCity.setId(city.getId())
                roomCity.setName(city.getName())
                Database.getInstance().getCityDao().insert(roomCity)
            }
        }
    }

    override fun addCities(cities: List<ICity>): Completable {
        return Completable.fromAction {
            for (city in cities) {
                var roomCity: RoomCity? = Database.getInstance().getCityDao().findByName(city.getName())
                if (roomCity == null) {
                    roomCity = RoomCity()
                    roomCity.setId(city.getId())
                    roomCity.setName(city.getName())
                    Database.getInstance().getCityDao().insert(roomCity)
                }
            }
        }
    }

    override fun removeCity(city: ICity): Completable {
        return Completable.fromAction {
            var roomCity: RoomCity? = Database.getInstance().getCityDao().findByName(city.getName())
            roomCity?.let {
                Database.getInstance().getCityDao().delete(it)
            }
        }
    }
}