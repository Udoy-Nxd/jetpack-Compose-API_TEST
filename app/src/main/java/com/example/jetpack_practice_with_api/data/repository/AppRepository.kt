package com.example.jetpack_practice_with_api.data.repository

import com.example.jetpack_practice_with_api.data.api.ApiInterface
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val api: ApiInterface
) {

    suspend fun fetchPost()= api.fetchPost()
}