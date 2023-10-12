package com.example.hiltunittest.data.source.remote.api

import com.example.hiltunittest.data.source.remote.dto.PexelsPhotoResponse
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * @author hafizdwp
 * 21/09/2023
 **/
interface PhotoRemoteApi {

    companion object {
        const val UNSPLASH_ACCESS_KEY = "wN85jysH7gKFebhlH_6aVFvhwjevgC-8xF_BkM2hN-Q"
        const val UNSPLASH_SECRET_KEY = "K0uY8R_eP23QnK_goPZclHc9gGqqp9hh5Ddsf6f4o7w"
//        const val BASE_URL = "https://api.unsplash.com/"
        const val BASE_URL = "https://api.pexels.com/v1/"
        const val PEXELS_API_KEY = "QjLyPjINzpnSEnhOOnE37059JeN3xmLQAfTbPGuLLO2VDf69pU8SOu97"
    }

//    @GET("photos/random?client_id=$UNSPLASH_ACCESS_KEY")
//    suspend fun getRandomPhotos(): UnsplashResponse

    @GET("curated")
    suspend fun getRandomPhotos(
            @Header("Authorization") key: String = PEXELS_API_KEY
    ): PexelsPhotoResponse
}