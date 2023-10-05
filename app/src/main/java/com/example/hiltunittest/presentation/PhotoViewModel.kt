package com.example.hiltunittest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltunittest.domain.model.Photo
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.util.DispatcherProvider
import com.example.hiltunittest.util.SingleLiveEvent
import com.example.hiltunittest.util.state.DataState
import com.example.hiltunittest.util.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author hafizdwp
 * 21/09/2023
 **/
@HiltViewModel
class PhotoViewModel @Inject constructor(
        private val getPhotoUsecase: GetPhotoUseCase,
        private val dispatcher: DispatcherProvider
) : ViewModel() {

    val eventViewState = SingleLiveEvent<ViewState>()
    val eventPhoto = SingleLiveEvent<List<Photo>?>()


    fun getPhoto() = viewModelScope.launch(dispatcher.io) {
        getPhotoUsecase().collect {
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