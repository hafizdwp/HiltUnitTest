package com.example.hiltunittest.domain.usecase

import com.example.hiltunittest.util.BaseTest
import com.example.hiltunittest.domain.repository.PhotoRepository
import com.example.hiltunittest.domain.model.Photo
import com.example.hiltunittest.domain.usecase.implementation.GetPhotoUseCaseImpl
import com.example.hiltunittest.util.state.DataState
import com.example.hiltunittest.util.state.ResultSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

/**
 * @author hafizdwp
 * 30/09/2023
 */
@ExperimentalCoroutinesApi
class GetPhotoUseCaseImplTest : BaseTest() {

    @Mock
    private lateinit var repository: PhotoRepository
    private lateinit var useCase: GetPhotoUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = GetPhotoUseCaseImpl(repository)
    }

    @Test
    fun testGetPhoto_emitLoading() = runTest {
        val mockResult = ResultSet.Success(listOf(
                Photo("asd")
        ))
        whenever(repository.getPhoto()).thenReturn(mockResult)

        val flowList = useCase.invoke().toList()
        assertEquals(flowList.size, 2)
        assertEquals(flowList[0]::class, DataState.Loading::class)
        assertEquals(flowList[1]::class, DataState.Success::class)
    }
}