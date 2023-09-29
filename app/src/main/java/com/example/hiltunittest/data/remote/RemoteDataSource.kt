package com.example.hiltunittest.data.remote

import com.example.hiltunittest.data.dto.PexelsResponse
import com.example.hiltunittest.util.ResultSet
import com.example.hiltunittest.util.log
import javax.inject.Inject

/**
 * @author hafizdwp
 * 21/09/2023
 **/
class RemoteDataSource @Inject constructor(
        private val api: RemoteApi
) {

    suspend fun getPhoto(): ResultSet<PexelsResponse> {
        return try {
            log("remote data source thread: ${Thread.currentThread().name}")
            ResultSet.Success(api.getRandomPhotos())
        } catch (e: Exception) {
            e.printStackTrace()
            ResultSet.Error(e.message ?: "")
        }
    }
}