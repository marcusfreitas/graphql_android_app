package com.example.githubreposearch.ui.view.activity

import android.app.Activity
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.githubreposearch.MainApplication
import com.example.githubreposearch.R
import com.example.githubreposearch.ui.home.presenter.HomeContract
import com.example.githubreposearch.ui.home.view.activity.HomeActivity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.util.*
import javax.inject.Provider


class HomeActivityTest {

    private val mockPresenter: HomeContract.Presenter = mock()
    private val query = "android"

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Rule
    @JvmField
    var activityTest = object : ActivityTestRule<HomeActivity>(HomeActivity::class.java) {
        override fun beforeActivityLaunched() {
            val application = context.applicationContext as MainApplication
            application.dispatchingActivityInjector = createFakeMainActivityInjector {
                presenter = mockPresenter
            }
        }
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


    fun createFakeMainActivityInjector(block: HomeActivity.() -> Unit)
            : DispatchingAndroidInjector<Activity> {
        val injector = AndroidInjector<Activity> { instance ->
            if (instance is HomeActivity) {
                instance.block()
            }
        }
        val factory = AndroidInjector.Factory<Activity> { injector }
        val map = mapOf(Pair<Class<*>,
                Provider<AndroidInjector.Factory<*>>>(HomeActivity::class.java, Provider { factory })
        )
        return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(map, Collections.emptyMap())
    }


}