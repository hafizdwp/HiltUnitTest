package com.example.hiltunittest.domain.usecase

import com.example.hiltunittest.domain.model.Photo
import com.example.hiltunittest.domain.repository.PhotoRepository
import com.example.hiltunittest.util.state.DataState
import com.example.hiltunittest.util.state.ResultSet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author hafizdwp
 * 22/09/2023
 **/

fun interface GetPhotoUseCase {
    operator fun invoke(): Flow<DataState<List<Photo>>>
}

class GetPhotoUseCaseImpl @Inject constructor(
        private val repository: PhotoRepository
) : GetPhotoUseCase {
    override fun invoke(): Flow<DataState<List<Photo>>> {
        return flow {
            emit(DataState.Loading)

            when (val response = repository.getPhoto()) {
                is ResultSet.Success -> {

                    if (response.data.isNullOrEmpty()) {
                        emit(DataState.Empty("kosong"))
                    } else {
                        emit(DataState.Success(response.data))
                    }
                }

                is ResultSet.Error -> {
                    emit(DataState.Error(response.errorMessage))
                }
            }
        }

    }
}