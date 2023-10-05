package com.example.hiltunittest.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

/**
 * @author hafizdwp
 * 30/09/2023
 **/
@ExperimentalCoroutinesApi
abstract class BaseTest {
    val testDispatchers = TestDispatchers()

    @get:Rule
    val rule = MainDispatcherRule(testDispatchers.testDispatcher)
}