package ru.vlabum.android.gb.xweather

import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.validateMockitoUsage
import org.mockito.MockitoAnnotations
import ru.vlabum.android.gb.xweather.di.DaggerTestComponent
import ru.vlabum.android.gb.xweather.di.module.TestRepoModule
import ru.vlabum.android.gb.xweather.mvp.model.entity.City
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.CurrentWeather
import ru.vlabum.android.gb.xweather.mvp.model.entity.weather.Main
import ru.vlabum.android.gb.xweather.mvp.model.image.IImageLoader
import ru.vlabum.android.gb.xweather.mvp.model.repo.IWeatherRepo
import ru.vlabum.android.gb.xweather.mvp.model.repo.WeatherRepo
import ru.vlabum.android.gb.xweather.mvp.presenter.MainPresenter
import ru.vlabum.android.gb.xweather.mvp.view.IMainView
import ru.vlabum.android.gb.xweather.ui.image.GlideImageLoader
import timber.log.Timber
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class MainPresenterTest {

    private lateinit var presenter: MainPresenter
    private lateinit var testScheduler: TestScheduler
    private lateinit var imageLoader: GlideImageLoader

    @Mock
    internal lateinit var mainView: IMainView


    companion object {

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            Timber.plant(Timber.DebugTree())
            Timber.d("setupClass")
        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            Timber.d("tearDownClass")
        }

    }

    @Before
    fun setup() {
        Timber.d("setup")
        MockitoAnnotations.initMocks(this)
        testScheduler = TestScheduler()
        imageLoader = Mockito.spy(GlideImageLoader(testScheduler))
        presenter = Mockito.spy(MainPresenter(testScheduler, imageLoader))
    }

    @After
    fun tearDown() {
        validateMockitoUsage()
        Timber.d("tearDown")
    }

    @Test
    fun loadDataSuccess() {
        val weather = CurrentWeather()
        weather.main = Main(40f, 700f, 70f)
        val city = City("Moscow")
        val component = DaggerTestComponent.builder()
            .testRepoModule(object : TestRepoModule() {
                override fun weatherRepo(): IWeatherRepo {
                    val repo = super.weatherRepo()
                    Mockito.`when`(repo.getWeather(city)).thenReturn(Single.just(weather))
                    return repo
                }
            })
            .build()

        component.inject(this)
        component.inject(presenter)
        presenter.attachView(mainView)
        Mockito.verify(mainView).init()
        Mockito.verify(mainView).showLoading()

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

//        Mockito.verify(mainView).hideLoading()
//        Mockito.verify(mainView, Mockito.times(2)).hideLoading()

//        Mockito.verify(presenter, Mockito.times(2)).getViewState()
//        Mockito.verify(presenter).initCity()
//
//        Mockito.verify(mainView).showLoading()
//        Mockito.verify(mainView).hideLoading()

//        Mockito.verify(mainView, Mockito.times(2)).showLoading()

//        Mockito.verify(presenter).initCity()
//        Mockito.verify(presenter).getViewState()
//        Mockito.verify(presenter).loadCities()
//        Mockito.verify(presenter).getViewState()
//        Mockito.verify(presenter, Mockito.times(1)).getViewState()

//        Mockito.verify(presenter).loadCities()
//        Mockito.verify(presenter, Mockito.times(1)).getViewState()
//        Mockito.verify(presenter).loadData(user.getLogin())
//
//        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
//
//        Mockito.verify(mainView, Mockito.times(1)).showLoading()
//        Mockito.verify<IUsersRepo>(usersRepo).getUser(user.getLogin())
//        Mockito.verify(mainView).setUsername(user.getLogin())
//        Mockito.verify(mainView).loadImage(user.getAvatarUrl())
//        Mockito.verify<IUsersRepo>(usersRepo).getUserRepos(user)
//        Mockito.verify(mainView).updateList()
//        Mockito.verify(mainView).hideLoading()
    }


}