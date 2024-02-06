package com.example.jetpack_practice_with_api.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_practice_with_api.data.model.Post
import com.example.jetpack_practice_with_api.data.repository.AppRepository
import com.example.jetpack_practice_with_api.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appRepo:AppRepository
):ViewModel() {
    private val _stateFlow = MutableStateFlow<Resource<ArrayList<Post.PostItem>>>(Resource.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = appRepo.fetchPost()
                _stateFlow.value = Resource.Success(response)
            } catch (e: Exception) {
                _stateFlow.value = Resource.Error("Failed to fetch data")
            }
        }
    }
}