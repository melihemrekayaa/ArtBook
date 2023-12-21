package com.mobile.artbook.di

import android.content.Context
import androidx.room.Room
import com.mobile.artbook.api.RetrofitAPI
import com.mobile.artbook.datasource.ArtDataSource
import com.mobile.artbook.repository.ArtRepository
import com.mobile.artbook.room.ArtDao
import com.mobile.artbook.room.ArtDatabase
import com.mobile.artbook.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getArtDataSource(adao: ArtDao) : ArtDataSource {
        return ArtDataSource(adao)
    }

    @Provides
    @Singleton
    fun getArtRepository(ads: ArtDataSource) : ArtRepository {
        return ArtRepository(ads)
    }

    @Provides
    @Singleton
    fun getArtDao(@ApplicationContext context : Context) : ArtDao {
        val vt = Room.databaseBuilder(
            context,ArtDatabase::class.java,"ArtBookDB"
        ).build()

        return vt.getArtDao()
    }

    @Provides
    @Singleton
    fun injectRetrofitAPI() : RetrofitAPI {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Util.BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)

        return retrofit
    }
}