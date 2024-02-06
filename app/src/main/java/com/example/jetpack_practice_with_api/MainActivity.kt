package com.example.jetpack_practice_with_api

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.jetpack_practice_with_api.data.model.Post
import com.example.jetpack_practice_with_api.utils.Resource
import com.example.jetpack_practice_with_api.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        val networkState by viewModel.stateFlow.collectAsState(initial = Resource.Loading)

        LaunchedEffect(Unit) {
            viewModel.fetchUsers()
        }

        when (networkState) {
            is Resource.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .padding(12.dp),
                    )
                }
            }

            is Resource.Success<*> -> {
                val postItemList = (networkState as Resource.Success<ArrayList<Post.PostItem>>).data
                LazyColumn {
                    itemsIndexed(items = postItemList) { index, item ->
                        MainPage(id = item.id ?: 0, title = item.title ?: "") {

                        }
                    }
                }
            }

            is Resource.Error -> {
                val errorMessage = (networkState as Resource.Error).errorMessage
                Text(text = errorMessage ?: "Error")
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