package com.example.hiltunittest.util

sealed class ResultSet<out R> {
    data class Success<out T>(val data: T?) : ResultSet<T>()
    data class Error(val errorMessage: String) : ResultSet<Nothing>()
}

fun <T> resultSuccess(data: T?): ResultSet<T> = ResultSet.Success(data)
fun resultError(e: Exception): ResultSet.Error {
    return ResultSet.Error(e.message ?: "")
}