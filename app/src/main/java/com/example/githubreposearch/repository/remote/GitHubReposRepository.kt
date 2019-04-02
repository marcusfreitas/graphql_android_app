package com.example.githubreposearch.repository.remote

import ListRepos
import com.apollographql.apollo.api.Response
import io.reactivex.Single

interface GitHubReposRepository {
    fun get(query: String): Single<Response<ListRepos.Data>>
}