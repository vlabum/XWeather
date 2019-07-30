package ru.vlabum.android.gb.xweather.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.CurrentWeather
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.IWeather

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface IMainView : MvpView {
    fun init()
    fun updateList()
    fun updateData(weather: IWeather)

    fun showLoading()
    fun hideLoading()

    fun setUsername(name: String)
    fun loadImage(url: String?)

    fun showMessage(message: String)
}