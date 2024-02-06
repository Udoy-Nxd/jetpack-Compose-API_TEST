package com.example.jetpack_practice_with_api

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

        var movieListResponse:List<Post.PostItem> by mutableStateOf(listOf())

        suspend fun fetchFlowData() {
            val viewModel: MainActivityViewModel by viewModels()
            viewModel.fetchFlowData().collect { resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {

                        movieListResponse = resource.data
                        Log.d("api_call_data", "fetchFlowData: ${resource.data}")
                    }

                    is Resource.Error -> {
                        Log.d("api_call_data", "fetchFlowData: error")
                    }
                }
            }
        }
        lifecycleScope.launch {
            fetchFlowData()
        }

        setContent {
            LazyColumn {
                itemsIndexed(items = movieListResponse) { index, item ->
                    PostItem(id = item.id!!, title = item.title!!)
                }
            }
        }


    }


    @Composable
    fun PostItem(
        id: Int = 0,
        title: String = ""
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RectangleShape)
                .fillMaxWidth()
                .wrapContentHeight()

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
//                    modifier = Modifier.padding(top= 4.dp)
                    )
                }

            }
        }

    }
}