package com.example.hiltunittest.di

import com.example.hiltunittest.data.repository.PhotoRepositoryImpl
import com.example.hiltunittest.data.source.local.PhotoLocalDataSource
import com.example.hiltunittest.data.source.remote.api.PhotoRemoteApi
import com.example.hiltunittest.data.source.remote.PhotoRemoteDataSource
import com.example.hiltunittest.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author hafizdwp
 * 05/10/2023
 **/
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideApi(): PhotoRemoteApi = Retrofit.Builder()
            .baseUrl(PhotoRemoteApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotoRemoteApi::class.java)

    @Singleton
    @Provides
    fun provideLocalDataSource(): PhotoLocalDataSource = PhotoLocalDataSource()

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: PhotoRemoteApi): PhotoRemoteDataSource = PhotoRemoteDataSource(api)

    @Singleton
    @Provides
    fun provideRepository(localDataSource: PhotoLocalDataSource,
                          remoteDataSource: PhotoRemoteDataSource): PhotoRepository =
            PhotoRepositoryImpl(localDataSource, remoteDataSource)
}