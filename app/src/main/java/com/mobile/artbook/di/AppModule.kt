package com.mobile.artbook.di

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobile.artbook.R
import com.mobile.artbook.api.RetrofitAPI
import com.mobile.artbook.repository.ArtRepository
import com.mobile.artbook.repository.ArtRepositoryInterface
import com.mobile.artbook.room.ArtDao
import com.mobile.artbook.room.ArtDatabase
import com.mobile.artbook.util.Util
import com.mobile.artbook.util.Util.BASE_URL
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
object AppModule {

    @Provides
    @Singleton
    fun injectRoomDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(context,ArtDatabase::class.java,"ArtBookDB").build()


    @Provides
    @Singleton
    fun injectDao(database: ArtDatabase) = database.getArtDao()

    @Provides
    @Singleton
    fun injectRetrofitAPI() : RetrofitAPI {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
    @Provides
    @Singleton
    fun injectNormalRepo(dao: ArtDao, api: RetrofitAPI) = ArtRepository(dao,api) as ArtRepositoryInterface
}