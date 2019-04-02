package com.example.githubreposearch.domain.usecase

import ListRepos
import com.apollographql.apollo.api.Response
import com.example.githubreposearch.domain.model.GitHubRepositoryModel
import com.example.githubreposearch.repository.remote.GitHubReposRepository
import io.reactivex.Single
import javax.inject.Inject

open class GitHubReposUseCase @Inject constructor(private val remoteRepository: GitHubReposRepository) {
    open fun get(query: String): Single<List<GitHubRepositoryModel>> {
        return remoteRepository.get(query)
            .map { response: Response<ListRepos.Data> ->
                val edges = response.data()?.search()?.edges()
                if (edges != null) {
                    val dataList = arrayListOf<GitHubRepositoryModel>()
                    for (edge in edges) {
                        dataList.add(
                            GitHubRepositoryModel(
                                edge.node()?.asRepository()?.owner()?.avatarUrl() as String,
                                edge.node()?.asRepository()?.name() ?: "",
                                edge.node()?.asRepository()?.description() ?: "",
                                edge.node()?.asRepository()?.forkCount() ?: 0
                            )
                        )
                    }

                    return@map dataList
                } else {
                    return@map null
                }
            }
    }
}