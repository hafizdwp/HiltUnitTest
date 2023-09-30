package com.example.hiltunittest.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author hafizdwp
 * 30/09/2023
 **/
interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}