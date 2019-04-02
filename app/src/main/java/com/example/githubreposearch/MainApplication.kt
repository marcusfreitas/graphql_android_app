package com.example.githubreposearch

import android.app.Application
import com.example.githubreposearch.di.component.AppComponent
import com.example.githubreposearch.di.component.DaggerAppComponent
import com.example.githubreposearch.di.module.AppModule

class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}