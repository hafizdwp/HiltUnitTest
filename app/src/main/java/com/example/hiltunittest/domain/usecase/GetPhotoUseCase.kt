package com.example.hiltunittest.domain.usecase

import com.example.hiltunittest.data.Repository
import com.example.hiltunittest.domain.model.ScreenPhoto
import com.example.hiltunittest.util.DataState
import com.example.hiltunittest.util.ResultSet
import com.example.hiltunittest.util.getThreadName
import com.example.hiltunittest.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @author hafizdwp
 * 22/09/2023
 **/
fun interface BaseUseCase<T> {
    suspend fun execute(): Flow<T>
}

interface GetPhotoUseCase : BaseUseCase<DataState<List<ScreenPhoto>>>

class GetPhotoUsecaseImpl @Inject constructor(
        private val repository: Repository
) : GetPhotoUseCase {
    override suspend fun execute(): Flow<DataState<List<ScreenPhoto>>> {
        return flow {
//            log("(execute) usecase flow: ${getThreadName()}")
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