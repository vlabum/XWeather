package ru.vlabum.android.gb.xweather

import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.TestCase.assertEquals
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import ru.vlabum.android.gb.xweather.di.ApiModule
import ru.vlabum.android.gb.xweather.di.CacheModule
import ru.vlabum.android.gb.xweather.di.DaggerTestComponentInst
import ru.vlabum.android.gb.xweather.di.RepoModule
import ru.vlabum.android.gb.xweather.mvp.model.entity.City
import ru.vlabum.android.gb.xweather.mvp.model.entity.ICity
import ru.vlabum.android.gb.xweather.mvp.model.repo.storage.IStorage
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject


open class WeatherInstrumentedTest {

//    @Inject
//    lateinit var weatherRepo: IWeatherRepo

    @Mock
    lateinit var storage: IStorage

    companion object {

        private var mockWebServer: MockWebServer? = null

        @BeforeClass
        @JvmStatic
        @Throws(IOException::class)
        fun setupClass() {
            Timber.d("setupClass")
            Timber.plant(Timber.DebugTree())
//            mockWebServer = MockWebServer()
//            mockWebServer!!.start()
        }

        @AfterClass
        @JvmStatic
        @Throws(IOException::class)
        fun tearDownClass() {
            Timber.d("tearDownClass")
//            mockWebServer!!.shutdown()
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Timber.d("setup")
        val component = DaggerTestComponentInst
            .builder()
            .repoModule(object : RepoModule() {
                override fun storage(): IStorage {
                    Mockito.`when`(storage.getCities())
                        .thenReturn(Single.just(arrayListOf(City("Moscow"), City("Chelyabinsk"))))
                    return storage
                }
            })
//            .apiModule(object : ApiModule() {
//                override fun baseUrl(): String {
//                    return mockWebServer!!.url("/").toString()
//                }
//            })
            .build()
        component.inject(this)
    }

    @After
    fun tearDown() {
        Timber.d("tearDown")
    }

    @Test
    fun initCity() {
        val city = City("Moscow")
        storage.addCity(city)
        val cities = storage.getCities()

        val observer = TestObserver<List<ICity>>()
        val a = storage.getCities()
        a.subscribe(observer)

        observer.assertValueCount(2)
        assertEquals(observer.values()[0][0].getName(), "Moscow")
        assertEquals(observer.values()[0][1].getName(), "Chelyabinsk")

    }
}
