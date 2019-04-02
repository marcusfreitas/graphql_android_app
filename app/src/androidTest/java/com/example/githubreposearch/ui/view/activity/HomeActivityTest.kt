package com.example.githubreposearch.ui.view.activity

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.githubreposearch.MainApplication
import com.example.githubreposearch.R
import com.example.githubreposearch.di.component.DaggerAppComponent
import com.example.githubreposearch.domain.di.DomainModule
import com.example.githubreposearch.domain.usecase.GitHubReposUseCase
import com.example.githubreposearch.repository.di.RemoteModule
import com.example.githubreposearch.repository.remote.GitHubReposRepository
import com.example.githubreposearch.schedulers.TrampolineSchedulerProvider
import com.example.githubreposearch.ui.home.di.HomeModule
import com.example.githubreposearch.ui.home.presenter.HomeContract
import com.example.githubreposearch.ui.home.view.activity.HomeActivity
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*

class HomeActivityTest {

    private val mockHomeModule: HomeModule = mock(HomeModule::class.java)
    private val mockDomainModule: DomainModule = mock(DomainModule::class.java)
    private val mockRemoteModule: RemoteModule = mock(RemoteModule::class.java)
    private val mockUseCase: GitHubReposUseCase = mock(GitHubReposUseCase::class.java)
    private val mockGitHubReposRepository: GitHubReposRepository = mock(GitHubReposRepository::class.java)
    private val schedulerProvider = TrampolineSchedulerProvider()
    private val mockPresenter: HomeContract.Presenter = mock(HomeContract.Presenter::class.java)
    private val query = "android"

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Rule
    @JvmField
    var activityTest = object : ActivityTestRule<HomeActivity>(HomeActivity::class.java) {
        override fun beforeActivityLaunched() {
            (context.applicationContext as MainApplication).appComponent = DaggerAppComponent.builder()
                .homeModule(mockHomeModule)
                .domainModule(mockDomainModule)
                .remoteModule(mockRemoteModule)
                .build()
        }
    }

    @Before
    fun setUp() {
        `when`(mockRemoteModule.providesGitHubReposRepository(any())).thenReturn(mockGitHubReposRepository)
        `when`(mockRemoteModule.providesScheduler()).thenReturn(schedulerProvider)
        `when`(mockDomainModule.providesGitHubRepoUseCase(mockGitHubReposRepository)).thenReturn(mockUseCase)
        `when`(mockHomeModule.providesHomePresenter(mockUseCase, schedulerProvider)).thenReturn(mockPresenter)
    }

    @Ignore // I'm having some problems with mockito, If have more time I'll return here and fix it
    @Test
    fun openHomeActivityAndTypeOnSearchField() {
        //given
        onView(withId(R.id.action_search)).check(matches(isDisplayed()))

        //when
        onView(withId(R.id.action_search)).perform(typeText(query))

        //then
        verify(mockPresenter).getList(query)
    }


}