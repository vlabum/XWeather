package ru.vlabum.android.gb.xweather.mvp.presenter

import android.annotation.SuppressLint
import android.widget.ImageView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.vlabum.android.gb.xweather.mvp.model.entity.City
import ru.vlabum.android.gb.xweather.mvp.model.entity.ICity
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.CurrentWeather
import ru.vlabum.android.gb.xweather.mvp.model.image.IImageLoader
import ru.vlabum.android.gb.xweather.mvp.model.repo.IWeatherRepo
import ru.vlabum.android.gb.xweather.mvp.model.repo.storage.IStorage
import ru.vlabum.android.gb.xweather.mvp.view.ICityRowView
import ru.vlabum.android.gb.xweather.mvp.view.IMainView
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
open class MainPresenter(val mainThreadScheduler: Scheduler, val imageLoader: IImageLoader) :
    MvpPresenter<IMainView>() {

    class CityListPresenter : ICityListPresenter {

        var clickSubjectVar = PublishSubject.create<ICityRowView>()
        override var cities: ArrayList<ICity> = arrayListOf()

        override fun bind(viewI: ICityRowView) {
            viewI.setName(cities[viewI.getPos()].getName())
        }

        override fun getCount(): Int {
            return cities.count()
        }

        override fun getClickSubject(): PublishSubject<ICityRowView> {
            return clickSubjectVar
        }

    }

    @Inject
    lateinit var storage: IStorage

    @Inject
    lateinit var weatherRepo: IWeatherRepo

    lateinit var currentWeather: CurrentWeather

    private var cityListPresenter: ICityListPresenter

    fun getCityListPresenter(): ICityListPresenter {
        return cityListPresenter
    }

    init {
        cityListPresenter = CityListPresenter()
    }


    @SuppressLint("CheckResult")
    override public fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        initCity()
//        loadCities()
        cityListPresenter.getClickSubject()
            .subscribe { cityRowView ->
//                viewState.showMessage(cityListPresenter.cities.get(cityRowView.getPos()).getName())
                loadWeather(cityListPresenter.cities.get(cityRowView.getPos()))
            }
    }

    fun addCity(cityName: String) {
        val d = storage.addCity(City(cityName))
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
            .subscribe(
                { viewState.updateList() },
                { e -> e.printStackTrace() }
            )
    }

    fun deleteCity(cityName: String) {
        val d = storage.removeCity(City(cityName))
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
            .subscribe(
                { viewState.updateList() },
                { e -> e.printStackTrace() }
            )
    }

    fun initCity() {
        //TODO: сделать проверку наличия городов в БД, если нет предложить ввести, иначе вывести из БД
        viewState.showLoading()
        val cities = arrayListOf(City("Moscow"), City("Chelyabinsk"))
        val d = storage.addCities(cities)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
            .subscribe(
                {
                    viewState.hideLoading()
                    loadCities()
                },
                { e ->
                    viewState.hideLoading()
                    e.message?.let { viewState.showMessage(it) }
                    e.printStackTrace()
                }
            )
    }

    fun loadCities() {
        viewState.showLoading()
        val d = storage.getCities()
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
            .subscribe(
                { loadedCities ->
                    cityListPresenter.cities.clear()
                    cityListPresenter.cities.addAll(loadedCities)
                    viewState.updateList()
                    viewState.hideLoading()
                },
                { e ->
                    viewState.hideLoading()
                    e.message?.let { viewState.showMessage(it) }
                    Timber.d(e.message)
                    e.printStackTrace()
                }
            )
    }

    fun loadWeather(city: ICity) {
        viewState.showLoading()
        val d = weatherRepo.getWeather(city)
            .map { loadedWeather ->
                imageLoader.checkLoaded(weatherRepo.getUrlIcon(loadedWeather))
                loadedWeather
            }
            .observeOn(mainThreadScheduler)
            .map { loadedWeather ->
                imageLoader.loadInto(weatherRepo.getUrlIcon(loadedWeather))
                loadedWeather
            }
            .subscribe(
                { loadedWeather ->
                    currentWeather = loadedWeather
                    viewState.hideLoading()
                    viewState.updateData(currentWeather)
                },
                { e ->
                    viewState.hideLoading()
                    e.message?.let { viewState.showMessage(it) }
                    e.printStackTrace()
                })
        return
    }
}