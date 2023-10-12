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

fun interface GetFavoritedPhotoUseCase {
    operator fun invoke(): Flow<DataState<List<Photo>>>
}