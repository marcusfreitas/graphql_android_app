package com.example.githubreposearch.di.component

import com.example.githubreposearch.di.module.AppModule
import com.example.githubreposearch.domain.di.DomainModule
import com.example.githubreposearch.repository.di.NetModule
import com.example.githubreposearch.repository.di.RemoteModule
import com.example.githubreposearch.ui.home.di.HomeModule
import com.example.githubreposearch.ui.home.view.activity.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, DomainModule::class, NetModule::class,
        RemoteModule::class, HomeModule::class]
)
interface AppComponent {
    fun inject(activity: HomeActivity)
}