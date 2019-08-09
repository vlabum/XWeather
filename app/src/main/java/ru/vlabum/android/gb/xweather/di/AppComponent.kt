package ru.vlabum.android.gb.xweather.di

import dagger.Component
import ru.vlabum.android.gb.xweather.mvp.presenter.MainPresenter
import ru.vlabum.android.gb.xweather.ui.image.GlideImageLoader
import javax.inject.Singleton

@Singleton
@Component(modules = [RepoModule::class, UtilsModule::class, CacheModule::class])
interface AppComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(glideImageLoader: GlideImageLoader)
}