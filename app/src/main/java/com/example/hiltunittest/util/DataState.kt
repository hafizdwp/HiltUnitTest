package com.example.hiltunittest.util

/**
 * Created by Kudzoza
 * on 07/07/2022
 **/

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    class Success<T>(val data: T?) : DataState<T>()
    class Empty(val emptyMsg: String): DataState<Nothing>()
    class Error(val errorMessage: String) : DataState<Nothing>()
}