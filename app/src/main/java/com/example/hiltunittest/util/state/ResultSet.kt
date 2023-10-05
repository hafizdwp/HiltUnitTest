package com.example.hiltunittest.util.state

sealed class ResultSet<out R> {
    data class Success<out T>(val data: T?) : ResultSet<T>()
    data class Error(val errorMessage: String) : ResultSet<Nothing>()
}