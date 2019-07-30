package ru.vlabum.android.gb.xweather.mvp.model.repo.storage

import io.reactivex.Completable
import io.reactivex.Single
import ru.vlabum.android.gb.xweather.mvp.model.entity.ICity

open interface IStorage {

    fun getCities(): Single<List<ICity>>

    fun addCity(city: ICity): Completable

    fun addCities(cities: List<ICity>): Completable

    fun removeCity(city: ICity): Completable

//    fun containsImage(name: String): Single<Boolean>

}