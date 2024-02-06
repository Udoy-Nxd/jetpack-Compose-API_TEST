package com.example.jetpack_practice_with_api.data.api

import com.example.jetpack_practice_with_api.data.model.Post
import retrofit2.http.GET

interface ApiInterface {

    @GET("posts")
    suspend fun fetchPost():ArrayList<Post.PostItem>

}