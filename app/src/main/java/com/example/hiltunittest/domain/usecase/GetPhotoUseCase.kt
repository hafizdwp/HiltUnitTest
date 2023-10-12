package com.example.hiltunittest.domain.usecase

import com.example.hiltunittest.domain.model.Photo
import com.example.hiltunittest.util.state.DataState
import kotlinx.coroutines.flow.Flow

/**
 * @author hafizdwp
 * 22/09/2023
 **/

fun interface GetPhotoUseCase {
    operator fun invoke(): Flow<DataState<List<Photo>>>
}