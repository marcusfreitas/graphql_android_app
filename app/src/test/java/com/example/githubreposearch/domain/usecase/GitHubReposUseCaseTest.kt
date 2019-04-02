package com.example.githubreposearch.domain.usecase

import ListRepos
import com.apollographql.apollo.api.Response
import com.example.githubreposearch.domain.model.GitHubRepositoryModel
import com.example.githubreposearch.repository.remote.GitHubReposRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class GitHubReposUseCaseTest {

    private lateinit var useCase: GitHubReposUseCase
    private val mockGitHubReposRepository: GitHubReposRepository = mock()
    private val query = "android"
    private val mockResponse: Response<ListRepos.Data> = mock()
    private val mockData: ListRepos.Data = mock()
    private val reposList = listOf<GitHubRepositoryModel>()

    @Before
    fun setUp() {
        useCase = GitHubReposUseCase(remoteRepository = mockGitHubReposRepository)
    }


    @Ignore // I wanted to test this class but I'm not familiar with Apollo (graphQL wrapper class) so I'm having some problems mocking some objects... need more time
    @Test
    fun `get git hub repos success`() {
        //given
        whenever(mockResponse.data()).thenReturn(mockData)
        whenever(mockGitHubReposRepository.get(query)).thenReturn(Single.just(mockResponse))

        // when
        val test = useCase.get(query).test()

        //then
        verify(mockGitHubReposRepository).get(query)
        test.assertValue(reposList)
    }
}