package com.example.githubreposearch.repository.remote

import ListRepos
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Single

class GitHubReposRepositoryImpl(val client: ApolloClient) : GitHubReposRepository {
    override fun get(query: String): Single<Response<ListRepos.Data>> {
        val call = client.query(ListRepos.builder().queryString(query).build())
        return Rx2Apollo.from(call)
    }
}