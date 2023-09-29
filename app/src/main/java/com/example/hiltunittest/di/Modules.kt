package com.example.hiltunittest.di

import android.content.Context
import com.example.hiltunittest.MyApp
import com.example.hiltunittest.data.Repository
import com.example.hiltunittest.data.RepositoryImpl
import com.example.hiltunittest.data.local.LocalDataSource
import com.example.hiltunittest.data.remote.RemoteApi
import com.example.hiltunittest.data.remote.RemoteDataSource
import com.example.hiltunittest.domain.usecase.GetPhotoUseCase
import com.example.hiltunittest.domain.usecase.GetPhotoUsecaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author hafizdwp
 * 21/09/2023
 **/
@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Singleton
    @Provides
    fun getPhotoUseCase(repository: Repository): GetPhotoUseCase {
        return GetPhotoUsecaseImpl(repository)
    }

    @Singleton
    @Provides
    fun provideApi(): RemoteApi = Retrofit.Builder()
            .baseUrl(RemoteApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RemoteApi::class.java)

    @Singleton
    @Provides
    fun providelocalDataSource(): LocalDataSource = LocalDataSource()

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: RemoteApi): RemoteDataSource = RemoteDataSource(api)

    @Singleton
    @Provides
    fun provideRepository(localDataSource: LocalDataSource,
                          remoteDataSource: RemoteDataSource): Repository =
            RepositoryImpl(localDataSource, remoteDataSource)

    @Singleton
    @Provides
    fun provideContext(): Context {
        return MyApp().applicationContext
    }
}