package com.example.hiltunittest.data.remote

import com.example.hiltunittest.data.dto.PexelsResponse
import com.example.hiltunittest.util.ResultSet
import com.example.hiltunittest.util.getThreadName
import com.example.hiltunittest.util.log
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * @author hafizdwp
 * 21/09/2023
 **/
class RemoteDataSource @Inject constructor(
        private val api: RemoteApi
) {

    suspend fun getPhoto(): ResultSet<PexelsResponse> {
//        delay(3_000)
//        log("(getPhoto) dataSource: ${getThreadName()}")
        return try {
            ResultSet.Success(api.getRandomPhotos())
        } catch (e: Exception) {
            ResultSet.Error(e.message ?: "")
        }
    }
}