package com.example.githubreposearch.ui.home.di

import com.example.githubreposearch.domain.di.DomainModule
import com.example.githubreposearch.domain.usecase.GitHubReposUseCase
import com.example.githubreposearch.schedulers.BaseSchedulerProvider
import com.example.githubreposearch.ui.home.presenter.HomeContract
import com.example.githubreposearch.ui.home.presenter.HomePresenter
import dagger.Module
import dagger.Provides

@Module(includes = [DomainModule::class])
open class HomeModule {

    @Provides
    open fun providesHomePresenter(
        useCase: GitHubReposUseCase,
        schedulerProvider: BaseSchedulerProvider
    ): HomeContract.Presenter = HomePresenter(useCase, schedulerProvider)
}