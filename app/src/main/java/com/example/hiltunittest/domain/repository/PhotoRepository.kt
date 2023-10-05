package com.example.hiltunittest.domain.repository

import com.example.hiltunittest.domain.model.Photo
import com.example.hiltunittest.util.state.ResultSet

/**
 * @author hafizdwp
 * 21/09/2023
 **/
interface PhotoRepository {
    suspend fun getPhoto(): ResultSet<List<Photo>>
}