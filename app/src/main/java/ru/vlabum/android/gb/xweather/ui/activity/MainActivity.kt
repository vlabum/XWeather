package ru.vlabum.android.gb.xweather.ui.activity

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main_bottom.*
import ru.vlabum.android.gb.xweather.App
import ru.vlabum.android.gb.xweather.R
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.IWeather
import ru.vlabum.android.gb.xweather.mvp.model.image.IImageLoader
import ru.vlabum.android.gb.xweather.mvp.model.repo.IWeatherRepo
import ru.vlabum.android.gb.xweather.mvp.presenter.MainPresenter
import ru.vlabum.android.gb.xweather.mvp.view.IMainView
import ru.vlabum.android.gb.xweather.ui.adapter.CitiesRVAdapter
import ru.vlabum.android.gb.xweather.ui.adapter.SimpleItemCallback
import ru.vlabum.android.gb.xweather.ui.image.GlideImageLoader

class MainActivity : MvpAppCompatActivity(), IMainView, AddCityFragmentDialog.AddCityDialogListener {

    override fun onDialogPositiveClick(cityName: String?) {
        if (cityName != null)
            presenter.addCity(cityName)
    }

    lateinit var adapter: CitiesRVAdapter

    val imageLoader = GlideImageLoader(AndroidSchedulers.mainThread())

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        App.getInstance().getAppComponent().inject(imageLoader)
        presenter = MainPresenter(AndroidSchedulers.mainThread(), imageLoader)
        App.getInstance().getAppComponent().inject(presenter)
        return presenter
    }

    override fun init() {
        city_name_rv.layoutManager = LinearLayoutManager(this)
        adapter = CitiesRVAdapter(presenter.getCityListPresenter())
        city_name_rv.adapter = ScaleInAnimationAdapter(adapter)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(city_name_rv)

        val itemTouchHelper = ItemTouchHelper(SimpleItemCallback(adapter))
        itemTouchHelper.attachToRecyclerView(city_name_rv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageLoader.setContainer(icon_iv)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            val addCityDialog: DialogFragment = AddCityFragmentDialog()
            addCityDialog.show(supportFragmentManager, "Добавление города")

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        loading_rl.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading_rl.visibility = View.GONE
    }

    override fun setUsername(name: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadImage(url: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateData(weather: IWeather) {
        weather_temp?.text = weather.getTemp().toString()
        weather_city?.text = weather.getCity()
        weather_precipitation_value?.text = weather.getDescription()
        weather_wind_value?.text = weather.getSpeedWind().toInt().toString()
        weather_humidity_value?.text = weather.getHumidity().toInt().toString()
        weather_pressure_value?.text = (weather.getPressure() / 1.333f).toInt().toString()
    }
}
