package com.example.hiltunittest.data.repository

import com.example.hiltunittest.data.source.local.PhotoLocalDataSource
import com.example.hiltunittest.data.source.remote.PhotoRemoteDataSource
import com.example.hiltunittest.domain.model.Photo
import com.example.hiltunittest.domain.repository.PhotoRepository
import com.example.hiltunittest.util.state.ResultSet
import javax.inject.Inject

/**
 * @author hafizdwp
 * 21/09/2023
 **/
class PhotoRepositoryImpl @Inject constructor(
        private val localDataSource: PhotoLocalDataSource,
        private val remoteDataSource: PhotoRemoteDataSource
) : PhotoRepository {

    override suspend fun getPhoto(): ResultSet<List<Photo>> {
        return when (val response = remoteDataSource.getPhoto()) {
            is ResultSet.Success -> {

                // mapping dto to domain
                val model = response.data?.photos?.map { rawPhotos ->
                    Photo(rawPhotos.src.large)
                } ?: listOf()
                ResultSet.Success(model)

            }

            is ResultSet.Error -> response
        }
    }

}