package ru.vlabum.android.gb.xweather.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.vlabum.android.gb.xweather.mvp.model.api.IDataSource
import javax.inject.Named
import javax.inject.Singleton

@Module
open class ApiModule {

    @Named("baseUrlWeather")
    @Singleton
    @Provides
    open fun baseUrl(): String {
        return "https://api.openweathermap.org/"
    }

    @Named("baseUrlImg")
    @Singleton
    @Provides
    open fun baseUrlImg(): String {
        return "https://openweathermap.org/img/w"
    }

    @Singleton
    @Provides
    fun gson(): Gson {
        return GsonBuilder()
//            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun api(@Named("baseUrlWeather") baseUrl: String, gson: Gson, @Named("clientLogging") okHttpClient: OkHttpClient): IDataSource {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IDataSource::class.java)
    }

    @Named("clientLogging")
    @Singleton
    @Provides
    fun okHttpClientLogging(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

}