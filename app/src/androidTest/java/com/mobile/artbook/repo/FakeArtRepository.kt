package com.mobile.artbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobile.artbook.model.ImageResponse
import com.mobile.artbook.repository.ArtRepositoryInterface
import com.mobile.artbook.room.Art
import com.mobile.artbook.util.Resource

class FakeArtRepositoryTest() : ArtRepositoryInterface{

    private val arts = mutableListOf<Art>()
    private val artLiveData = MutableLiveData<List<Art>>(arts)


    override suspend fun insertArt(art: Art) {
        arts.add(art)
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }


}