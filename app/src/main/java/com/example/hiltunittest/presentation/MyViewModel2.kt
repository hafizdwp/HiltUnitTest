package com.example.hiltunittest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltunittest.domain.model.ScreenPhoto
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.util.DataState
import com.example.hiltunittest.util.SingleLiveEvent
import com.example.hiltunittest.util.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author hafizdwp
 * 21/09/2023
 **/
@HiltViewModel
class MyViewModel2 @Inject constructor(
        val getPhotoUsecase: GetPhotoUseCase
) : ViewModel() {

    val eventViewState = SingleLiveEvent<ViewState>()
    val eventPhoto = SingleLiveEvent<List<ScreenPhoto>?>()


    fun getPhoto() = viewModelScope.launch(Dispatchers.IO) {
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
    }
}