package com.example.hiltunittest.data.remote

import com.example.hiltunittest.data.remote.dto.PexelsPhotoResponse
import com.example.hiltunittest.util.state.ResultSet
import javax.inject.Inject

/**
 * @author hafizdwp
 * 21/09/2023
 **/
class PhotoRemoteDataSource @Inject constructor(
        private val api: PhotoRemoteApi
) {

    suspend fun getPhoto(): ResultSet<PexelsPhotoResponse> {
        return try {
            ResultSet.Success(api.getRandomPhotos())
        } catch (e: Exception) {
            ResultSet.Error(e.message ?: "")
        }
    }
}