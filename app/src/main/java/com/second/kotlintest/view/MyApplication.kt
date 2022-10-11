package com.second.kotlintest.view

import android.app.Application
import com.second.kotlintest.di.DaggerRetroComponent
import com.second.kotlintest.di.RetroComponent
import com.second.kotlintest.di.RetroModule

class MyApplication: Application() {

    private lateinit var retroComponent: RetroComponent

    override fun onCreate() {
        super.onCreate()

        retroComponent = DaggerRetroComponent.builder()
            .retroModule(RetroModule())
            .build()
    }

    fun getRetroComponent(): RetroComponent {
        return retroComponent
    }
}