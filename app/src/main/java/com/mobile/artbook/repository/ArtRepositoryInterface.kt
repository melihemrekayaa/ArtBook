package com.mobile.artbook.repository

import androidx.lifecycle.LiveData
import com.mobile.artbook.model.ImageResponse
import com.mobile.artbook.room.Art
import com.mobile.artbook.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>


}