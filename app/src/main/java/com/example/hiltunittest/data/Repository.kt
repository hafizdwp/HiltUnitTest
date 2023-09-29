package com.example.hiltunittest.data

import com.example.hiltunittest.domain.model.ScreenPhoto
import com.example.hiltunittest.util.ResultSet

/**
 * @author hafizdwp
 * 21/09/2023
 **/
interface Repository {
    suspend fun getPhoto(): ResultSet<List<ScreenPhoto>>
}