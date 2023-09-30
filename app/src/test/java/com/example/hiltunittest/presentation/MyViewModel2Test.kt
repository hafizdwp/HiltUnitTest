package com.example.hiltunittest.presentation

import com.example.hiltunittest.domain.model.ScreenPhoto
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.util.DataState
import com.example.hiltunittest.util.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * @author hafizdwp
 * 30/09/2023
 */
@ExperimentalCoroutinesApi
class MyViewModel2Test {

    @Mock
    private lateinit var getPhotoUseCase: GetPhotoUseCase

    private lateinit var viewModel: MyViewModel2

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = MyViewModel2(getPhotoUseCase)
    }

    @Test
    fun testGetPhoto_resultSuccess() = runTest {
        val mockResponse = listOf(
                ScreenPhoto("asd"),
                ScreenPhoto("asd2")
        )
        val successState = ViewState.Success
        Mockito.`when`(getPhotoUseCase.execute()).thenReturn(flowOf(DataState.Success(mockResponse)))

        viewModel.getPhoto()
        advanceUntilIdle()

        Mockito.verify(getPhotoUseCase, Mockito.times(1)).execute()

        Assert.assertEquals(viewModel.eventViewState.value!!::class, successState::class)
//        assertEquals(viewModel.eventViewState.value, null)
        Assert.assertEquals(viewModel.eventPhoto.value, mockResponse)
    }

    @Test
    fun testGetPhoto_resultError() = runTest {
        val errorMsg = "error"
        val expectedViewState = ViewState.Failed(errorMsg)
        Mockito.`when`(getPhotoUseCase.execute()).thenReturn(flowOf(DataState.Error(errorMsg)))

        viewModel.getPhoto()
        advanceUntilIdle()

        Mockito.verify(getPhotoUseCase, Mockito.times(1)).execute()

        Assert.assertEquals(viewModel.eventViewState.value!!::class, expectedViewState::class)
        Assert.assertEquals(viewModel.eventPhoto.value, null)
    }

    @Test
    fun testGetPhoto_resultEmpty() = runTest {
        val emptyMsg = "empty"
        val expectedViewState = ViewState.Empty(emptyMsg)
        Mockito.`when`(getPhotoUseCase.execute()).thenReturn(flowOf(DataState.Empty(emptyMsg)))

        viewModel.getPhoto()
        advanceUntilIdle()

        Mockito.verify(getPhotoUseCase, Mockito.times(1)).execute()

        Assert.assertEquals(viewModel.eventViewState.value!!::class, expectedViewState::class)
        Assert.assertEquals(viewModel.eventPhoto.value, null)
    }

    @Test
    fun testGetPhoto_loading() = runTest {
        val dataStateLoading = DataState.Loading
        Mockito.`when`(getPhotoUseCase.execute()).thenReturn(flowOf(dataStateLoading))

        viewModel.getPhoto()
        advanceUntilIdle()

        Mockito.verify(getPhotoUseCase, Mockito.times(1)).execute()
        val loading = getPhotoUseCase.execute().first()

        Assert.assertEquals(dataStateLoading::class, loading::class)
    }
}