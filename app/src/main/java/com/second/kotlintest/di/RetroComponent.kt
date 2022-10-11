package com.second.kotlintest.di

import com.second.kotlintest.view.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [RetroModule::class])
interface RetroComponent {

    fun inject(mainActivityViewModel: MainActivityViewModel)
}