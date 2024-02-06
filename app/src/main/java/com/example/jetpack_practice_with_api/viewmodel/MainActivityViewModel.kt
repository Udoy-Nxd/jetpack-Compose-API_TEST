package com.example.jetpack_practice_with_api.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.jetpack_practice_with_api.data.model.Post
import com.example.jetpack_practice_with_api.data.repository.AppRepository
import com.example.jetpack_practice_with_api.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appRepo:AppRepository
):ViewModel() {



//    val postListValue:ArrayList<Post.PostItem> = arrayListOf()
    var movieListResponse:List<Post.PostItem> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun fetchFlowData(): Flow<Resource<ArrayList<Post.PostItem>>> = flow{

        try {
            emit(Resource.Loading)
            val posts = appRepo.fetchPost()
            emit(Resource.Success(posts))

        } catch (exp: Exception) {
            emit(Resource.Error(exp.message))
        }

    }.flowOn(Dispatchers.IO)

}