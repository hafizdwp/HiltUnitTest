package com.example.hiltunittest.di

import com.example.hiltunittest.domain.repository.PhotoRepository
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.domain.usecase.GetPhotoUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author hafizdwp
 * 05/10/2023
 **/
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun getPhotoUseCase(repository: PhotoRepository): GetPhotoUseCase {
        return GetPhotoUseCaseImpl(repository)
    }
}