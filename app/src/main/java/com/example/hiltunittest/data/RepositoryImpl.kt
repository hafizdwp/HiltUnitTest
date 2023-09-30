package com.example.hiltunittest.data

import com.example.hiltunittest.data.local.LocalDataSource
import com.example.hiltunittest.data.remote.RemoteDataSource
import com.example.hiltunittest.domain.model.ScreenPhoto
import com.example.hiltunittest.util.ResultSet
import com.example.hiltunittest.util.getThreadName
import com.example.hiltunittest.util.log
import javax.inject.Inject

/**
 * @author hafizdwp
 * 21/09/2023
 **/
class RepositoryImpl @Inject constructor(
        private val localDataSource: LocalDataSource,
        private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getPhoto(): ResultSet<List<ScreenPhoto>> {
//        log("(getPhoto) repo impl: ${getThreadName()}")
        return when (val response = remoteDataSource.getPhoto()) {
            is ResultSet.Success -> {
                val model = response.data?.photos?.map { rawPhotos ->
                    ScreenPhoto(rawPhotos.src.large)
                } ?: listOf()
                ResultSet.Success(model)
            }

            is ResultSet.Error -> {
                ResultSet.Error(response.errorMessage)
            }
        }
    }

}