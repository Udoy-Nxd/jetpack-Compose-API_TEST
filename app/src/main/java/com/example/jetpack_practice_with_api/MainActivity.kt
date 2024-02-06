package com.example.jetpack_practice_with_api

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.jetpack_practice_with_api.data.model.Post
import com.example.jetpack_practice_with_api.utils.Resource
import com.example.jetpack_practice_with_api.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainBody()
        }
    }

    @SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
    @Composable
    fun MainBody() {
        val viewModel: MainActivityViewModel by viewModels()
        val isLoading: MutableState<Boolean> = mutableStateOf(false)
        var postItemList: MutableState<List<Post.PostItem>> = mutableStateOf(emptyList())

        LaunchedEffect(key1 = viewModel) {
            viewModel.fetchFlowData().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.value = true
                        Log.e("TAG", "MainBody: Loading")
                    }

                    is Resource.Success -> {
//                        Log.e("TAG", "MainBody: Success")
//
//                        Log.e("TAG", "post item: ${postItemList.size}")
//                        isLoading.value = false
//                        postItemList = resource.data
//                        Log.e("TAG", "after api call post item: ${postItemList.size}")

                        Log.e("TAG", "MainBody: Success")

                        isLoading.value = false
                        postItemList.value = resource.data // Update postItemList using value property

                        Log.e("TAG", "after api call post item: ${postItemList.value.size}")

                    }

                    is Resource.Error -> {
                        isLoading.value = false
                    }
                }
            }
        }

        Log.e("TAG", "MainBody: $isLoading")
//        LazyColumn {
//            itemsIndexed(items = postItemList) { index, item ->
//                MainPage(id = item.id ?: 0, title = item.title ?: ""){
//
//                }
//            }
//        }
        uiLogic(isLoading, postItemList)

    }

    @Composable
    fun uiLogic(
        isLoading: MutableState<Boolean>,
        postItemList: MutableState<List<Post.PostItem>>
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .padding(12.dp)
            )
        } else {
//            Log.e("TAG", "uiLogic: ${postItemList.size}")
            val postItemList = postItemList.value
            LazyColumn {
                itemsIndexed(items = postItemList) { index, item ->
                    MainPage(id = item.id ?: 0, title = item.title ?: ""){

                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainPage(
        id: Int = 0,
        title: String = "",
        onClick: () -> Unit = {}
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RectangleShape)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onClick.invoke() }
        ) {
            Row(
                Modifier
                    .padding(12.dp)
                    .background(Color.Blue)
                    .fillMaxWidth()
            ) {

                Image(
                    painterResource(id = R.drawable.ic_elon),
                    contentDescription = "null",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape)
                )

                Column(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,


                    ) {
                    Text(
                        text = "Profile ID: $id",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Title : $title", fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}