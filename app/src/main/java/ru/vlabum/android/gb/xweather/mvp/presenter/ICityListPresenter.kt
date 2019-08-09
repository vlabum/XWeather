package ru.vlabum.android.gb.xweather.mvp.presenter

import io.reactivex.subjects.PublishSubject
import ru.vlabum.android.gb.xweather.mvp.model.entity.ICity
import ru.vlabum.android.gb.xweather.mvp.view.ICityRowView

interface ICityListPresenter {
    var cities: ArrayList<ICity>
    fun bind(viewI: ICityRowView)
    fun getCount(): Int
    fun getClickSubject(): PublishSubject<ICityRowView>
}