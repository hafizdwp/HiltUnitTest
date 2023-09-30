package com.example.hiltunittest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltunittest.domain.model.ScreenPhoto
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.system.measureTimeMillis

/**
 * @author hafizdwp
 * 21/09/2023
 **/
@HiltViewModel
class MyViewModel @Inject constructor(
        val getPhotoUsecase: GetPhotoUseCase,
        private val dispatcher: DispatcherProvider
) : ViewModel() {

    val eventViewState = SingleLiveEvent<ViewState>()
    val eventPhoto = SingleLiveEvent<List<ScreenPhoto>?>()

    fun getPhoto() = viewModelScope.launch(dispatcher.io) {
//        async(dispatcher.io) {
            getPhotoUsecase.execute().collect {
//            log("(getPhoto) viewmodel collect: ${getThreadName()}")
                when (it) {
                    is DataState.Loading -> {
                        eventViewState.postValue(ViewState.Loading)
                    }

                    is DataState.Error -> {
                        eventViewState.postValue(ViewState.Failed(it.errorMessage))
                    }

                    is DataState.Empty -> {
                        eventViewState.postValue(ViewState.Empty(it.emptyMsg))
                    }

                    is DataState.Success -> {
                        eventViewState.postValue(ViewState.Success)

                        eventPhoto.postValue(it.data)
                    }
                }
            }
//        }.await()
//        }
    }

    val eventCountdown = SingleLiveEvent<Int>()

    fun heavyOperation() = viewModelScope.launch {
//        log("(heavyOperation) BEFORE ${getThreadName()}")
//        heavyFlow().collect { countdown ->
//            eventCountdown.value = countdown
//        }
//        log("(heavyOperation) AFTER ${getThreadName()}")
    }

    private fun heavyFlow() = flow<Int> {
        repeat(10) {
            delay(1000)
            log("(heavyFlow) logging repeat $it, on ${getThreadName()}")
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

    fun getPhotoSync() = viewModelScope.launch {
        getPhotoUsecase.execute().collect {
            when (it) {
                is DataState.Loading -> {
                    eventViewState.postValue(ViewState.Loading)
                }

                is DataState.Success -> {
                    eventViewState.postValue(ViewState.Success)

                    eventPhoto.postValue(it.data)
                }

                is DataState.Empty -> {
                    eventViewState.postValue(ViewState.Empty(it.emptyMsg))
                }

                is DataState.Error -> {
                    eventViewState.postValue(ViewState.Failed(it.errorMessage))
                }

                else -> {
                    // Nothing
                }
            }
        }
    }

    fun performHeavyCompute() {
        main()
    }

    fun performHeavyComputation(iterations: Int): Long {
        var result = 0L
        for (i in 0 until iterations) {
            // Simulate a heavy computation task
            result += fibonacci(30)
        }
        return result
    }

    fun fibonacci(n: Int): Long {
        return if (n <= 1) n.toLong() else fibonacci(n - 1) + fibonacci(n - 2)
    }

    fun main() {
        val iterations = 500
        val mainThreadTime = measureTimeMillis {
            val result = performHeavyComputation(iterations)
            log("main result: ${Thread.currentThread().name} -> $result")
        }

        log("time taken on main: $mainThreadTime ms")

        val ioThreadTime = measureTimeMillis {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val result = performHeavyComputation(iterations)
                    log("io result: ${Thread.currentThread().name} -> $result")
                }
            }
        }
        log("PRINTIN MAIN AFTER FIRING IO")
        log("time taken on io: $ioThreadTime ms")
    }
}