package com.example.hiltunittest

import com.example.hiltunittest.domain.model.ScreenPhoto
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.presentation.MyViewModel
import com.example.hiltunittest.util.DataState
import com.example.hiltunittest.util.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
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
class MyViewModelTest : BaseTest() {

    @Mock
    private lateinit var getPhotoUseCase: GetPhotoUseCase

    private lateinit var viewModel: MyViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = MyViewModel(getPhotoUseCase, testDispatchers)
    }

    @Test
    fun testGetPhoto_resultSuccess() = runTest {
        val mockResponse = listOf(
                ScreenPhoto("asd"),
                ScreenPhoto("asd2")
        )
        val successState = ViewState.Success
        `when`(getPhotoUseCase.execute()).thenReturn(flowOf(DataState.Success(mockResponse)))

        viewModel.getPhoto()

        verify(getPhotoUseCase, times(1)).execute()

        assertEquals(viewModel.eventViewState.value!!::class, successState::class)
//        assertEquals(viewModel.eventViewState.value, null)
        assertEquals(viewModel.eventPhoto.value, mockResponse)
    }

    @Test
    fun testGetPhoto_resultError() = runTest {
        val errorMsg = "error"
        val expectedViewState = ViewState.Failed(errorMsg)
        `when`(getPhotoUseCase.execute()).thenReturn(flowOf(DataState.Error(errorMsg)))

        viewModel.getPhoto()

        verify(getPhotoUseCase, times(1)).execute()

        assertEquals(viewModel.eventViewState.value!!::class, expectedViewState::class)
        assertEquals(viewModel.eventPhoto.value, null)
    }

    @Test
    fun testGetPhoto_resultEmpty() = runTest {
        val emptyMsg = "empty"
        val expectedViewState = ViewState.Empty(emptyMsg)
        `when`(getPhotoUseCase.execute()).thenReturn(flowOf(DataState.Empty(emptyMsg)))

        viewModel.getPhoto()

        verify(getPhotoUseCase, times(1)).execute()

        assertEquals(viewModel.eventViewState.value!!::class, expectedViewState::class)
        assertEquals(viewModel.eventPhoto.value, null)
    }

    @Test
    fun testGetPhoto_loading() = runTest {
        val dataStateLoading = DataState.Loading
        `when`(getPhotoUseCase.execute()).thenReturn(flowOf(dataStateLoading))

        viewModel.getPhoto()

        verify(getPhotoUseCase, times(1)).execute()
        val loading = getPhotoUseCase.execute().first()

        assertEquals(dataStateLoading::class, loading::class)
    }
}