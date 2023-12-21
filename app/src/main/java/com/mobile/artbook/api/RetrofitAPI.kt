package com.mobile.artbook.api

import com.mobile.artbook.model.ImageResponse
import com.mobile.artbook.util.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("/api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey : String = Util.API_KEY
    ) : Response<ImageResponse>
}