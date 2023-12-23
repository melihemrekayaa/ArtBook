package com.mobile.artbook.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mobile.artbook.getOrAwaitValue
import com.mobile.artbook.room.Art
import com.mobile.artbook.room.ArtDao
import com.mobile.artbook.room.ArtDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ArtDatabase

    private lateinit var artDao: ArtDao

    @Before
    fun setup(){
        /*database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
        ArtDatabase::class.java
            ).allowMainThreadQueries().build()*/

        hiltRule.inject()

        artDao = database.getArtDao()
    }

    @After
    fun teardown(){
        database.close()
    }


    @Test
    fun insertArtTesting() = runTest {
        val exampleArt = Art("Mona Lisa","Da vinci",1800,"test.com",1)
        artDao.insertArt(exampleArt)

        val list = artDao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTesting() = runTest {
        val exampleArt = Art("Mona Lisa","Da vinci",1800,"test.com",1)
        artDao.insertArt(exampleArt)
        artDao.deleteArt(exampleArt)

        val list = artDao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)
    }



}