package com.mobile.artbook.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.android.apps.common.testing.accessibility.framework.utils.contrast.Image
import com.google.common.truth.Truth.assertThat
import com.mobile.artbook.launchFragmentInHiltContainer
import com.mobile.artbook.ui.fragment.ArtFragmentFactory
import com.mobile.artbook.ui.fragment.ImageApiFragment
import com.mobile.artbook.R
import com.mobile.artbook.getOrAwaitValue
import com.mobile.artbook.repo.FakeArtRepositoryTest
import com.mobile.artbook.room.Art
import com.mobile.artbook.ui.fragment.ArtDetailsFragment
import com.mobile.artbook.ui.fragment.ArtDetailsFragmentDirections
import com.mobile.artbook.ui.viewmodel.ArtViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtImageViewToImageApiFragment(){
        val navController = Mockito.mock(NavController::class.java)

            launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
    }

    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun testSave(){
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<ArtDetailsFragment>(factory = fragmentFactory){
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.nameText)).perform(ViewActions.replaceText("Mona Lisa"))
        Espresso.onView(ViewMatchers.withId(R.id.artistText)).perform(ViewActions.replaceText("Da Vinci"))
        Espresso.onView(ViewMatchers.withId(R.id.yearText)).perform(ViewActions.replaceText("1000"))
        Espresso.onView(ViewMatchers.withId(R.id.saveBtn)).perform(ViewActions.click())

        assertThat(testViewModel.artList.getOrAwaitValue()).contains(
            Art("Mona Lisa","Da Vinci",1000,"")
        )

    }
}