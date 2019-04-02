package com.example.githubreposearch.ui.presenter

import com.example.githubreposearch.domain.model.GitHubRepositoryModel
import com.example.githubreposearch.domain.usecase.GitHubReposUseCase
import com.example.githubreposearch.schedulers.TrampolineSchedulerProvider
import com.example.githubreposearch.ui.home.presenter.HomeContract
import com.example.githubreposearch.ui.home.presenter.HomePresenter
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class HomePresenterTest {

    private lateinit var presenter: HomePresenter
    private val mockUseCase: GitHubReposUseCase = mock()
    private val mockView: HomeContract.View = mock()
    private val schedulerProvider = TrampolineSchedulerProvider()
    private val query = "android"
    private val repoList = listOf(
        GitHubRepositoryModel(
            avatarUrl = "http://test/test.jpg", name = "AndroidLib",
            description = "awesome lib", forksCount = 10
        ),
        GitHubRepositoryModel(
            avatarUrl = "http://test2/test2.jpg", name = "WidgetLib",
            description = "awesome widget lib", forksCount = 100
        )

    )

    @Before
    fun setUp() {
        presenter = HomePresenter(mockUseCase, schedulerProvider)
    }

    @Test
    fun `successfully attach view`() {
        //when
        presenter.attach(mockView)

        //then
        assert(presenter.view != null)
    }

    @Test
    fun `successfully get a list of repos`() {
        //given
        given(mockUseCase.get(query)).willReturn(Single.just(repoList))
        presenter.attach(mockView)

        //when
        presenter.getList(query)

        //then
        then(mockUseCase).should().get(query)
        then(mockView).should().setupRecyclerView(repoList)
    }

    @Test
    fun `error at getting a list of repos`() {
        //given
        given(mockUseCase.get(query)).willReturn(Single.error(Throwable("error loading data")))
        presenter.attach(mockView)

        //when
        presenter.getList(query)

        //then
        then(mockUseCase).should().get(query)
        then(mockView).should().showLoadDataError()
    }

    @Test
    fun `click on item on list should open detail activity`() {
        //given
        val repository = repoList[0]
        presenter.attach(mockView)

        //when
        presenter.itemClick(repository)

        //then
        then(mockView).should().openDetailActivity(repository)
    }
}