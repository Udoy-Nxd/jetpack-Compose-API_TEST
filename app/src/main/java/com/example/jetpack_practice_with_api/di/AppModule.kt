package com.example.jetpack_practice_with_api.di

import com.example.jetpack_practice_with_api.data.api.ApiInterface
import com.example.jetpack_practice_with_api.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideInterface(): ApiInterface {
        return ApiClient.getRetrofit().create(ApiInterface::class.java)
    }
}