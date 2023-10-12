package com.example.hiltunittest.presentation

import com.example.hiltunittest.domain.model.Photo
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.util.BaseTest
import com.example.hiltunittest.util.state.DataState
import com.example.hiltunittest.util.state.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * @author hafizdwp
 * 29/09/2023
 **/

@ExperimentalCoroutinesApi
class PhotoViewModelTest : BaseTest() {

    @Mock
    private lateinit var getPhotoUseCase: GetPhotoUseCase

    private lateinit var viewModel: PhotoViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = PhotoViewModel(getPhotoUseCase, testDispatchers)
    }

    @Test
    fun getPhoto_loading() = runTest {
        val dataStateLoading = DataState.Loading
        `when`(getPhotoUseCase.invoke()).thenReturn(flowOf(dataStateLoading))

        viewModel.getPhoto()

        verify(getPhotoUseCase, times(1)).invoke()
        val loading = getPhotoUseCase.invoke().first()

        assertEquals(dataStateLoading::class, loading::class)
    }

    @Test
    fun getPhoto_success() = runTest {
        val mockResponse = listOf(
                Photo("asd"),
                Photo("asd2")
        )
        val successState = ViewState.Success
        `when`(getPhotoUseCase.invoke()).thenReturn(flowOf(DataState.Success(mockResponse)))

        viewModel.getPhoto()

        verify(getPhotoUseCase, times(1)).invoke()

        assertEquals(viewModel.eventViewState.value!!::class, successState::class)
//        assertEquals(viewModel.eventViewState.value, null)
        assertEquals(viewModel.eventPhoto.value, mockResponse)
    }

    @Test
    fun getPhoto_error() = runTest {
        val errorMsg = "error"
        val expectedViewState = ViewState.Failed(errorMsg)
        `when`(getPhotoUseCase.invoke()).thenReturn(flowOf(DataState.Error(errorMsg)))

        viewModel.getPhoto()

        verify(getPhotoUseCase, times(1)).invoke()

        assertEquals(viewModel.eventViewState.value!!::class, expectedViewState::class)
        assertEquals(viewModel.eventPhoto.value, null)
    }

    @Test
    fun getPhoto_empty() = runTest {
        val emptyMsg = "empty"
        val expectedViewState = ViewState.Empty(emptyMsg)
        `when`(getPhotoUseCase.invoke()).thenReturn(flowOf(DataState.Empty(emptyMsg)))

        viewModel.getPhoto()

        verify(getPhotoUseCase, times(1)).invoke()

        assertEquals(viewModel.eventViewState.value!!::class, expectedViewState::class)
        assertEquals(viewModel.eventPhoto.value, null)
    }
}