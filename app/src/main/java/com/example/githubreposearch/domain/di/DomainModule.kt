package com.example.githubreposearch.domain.di

import com.example.githubreposearch.domain.usecase.GitHubReposUseCase
import com.example.githubreposearch.repository.di.RemoteModule
import com.example.githubreposearch.repository.remote.GitHubReposRepository
import dagger.Module
import dagger.Provides

@Module(includes = [RemoteModule::class])
class DomainModule {

    @Provides
    fun providesGitHubRepoUseCase(repository: GitHubReposRepository) = GitHubReposUseCase(repository)
}