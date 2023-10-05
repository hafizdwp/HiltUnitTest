package com.example.hiltunittest.di

import android.content.Context
import com.example.hiltunittest.MyApp
import com.example.hiltunittest.util.CoreDispatcher
import com.example.hiltunittest.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author hafizdwp
 * 21/09/2023
 **/
@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider {
        return CoreDispatcher()
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return MyApp().applicationContext
    }
}