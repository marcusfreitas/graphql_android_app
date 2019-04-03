package com.example.githubreposearch.di.module

import com.example.githubreposearch.ui.home.di.HomeModule
import com.example.githubreposearch.ui.home.view.activity.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun bindHomeActivity(): HomeActivity
}