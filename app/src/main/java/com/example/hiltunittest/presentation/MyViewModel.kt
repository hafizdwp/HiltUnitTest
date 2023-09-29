package com.example.hiltunittest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltunittest.domain.model.ScreenPhoto
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.util.DataState
import com.example.hiltunittest.util.SingleLiveEvent
import com.example.hiltunittest.util.ViewState
import com.example.hiltunittest.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.system.measureTimeMillis

/**
 * @author hafizdwp
 * 21/09/2023
 **/
@HiltViewModel
class MyViewModel @Inject constructor(
        val getPhotoUsecase: GetPhotoUseCase
) : ViewModel() {

    val eventViewState = SingleLiveEvent<ViewState>()
    val eventPhoto = SingleLiveEvent<List<ScreenPhoto>?>()


    fun getPhoto() = viewModelScope.launch {
        async(Dispatchers.IO) {
            getPhotoUsecase.execute().collect {
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
        }.await()
    }

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