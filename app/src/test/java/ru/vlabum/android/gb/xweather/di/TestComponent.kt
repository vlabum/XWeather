package ru.vlabum.android.gb.xweather.di

import dagger.Component
import ru.vlabum.android.gb.xweather.MainPresenterTest
import ru.vlabum.android.gb.xweather.di.module.TestRepoModule
import ru.vlabum.android.gb.xweather.mvp.presenter.MainPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [TestRepoModule::class])
interface TestComponent {
    fun inject(presenter: MainPresenter)
    fun inject(mainPresenterTest: MainPresenterTest)
}