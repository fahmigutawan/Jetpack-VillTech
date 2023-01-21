package com.ub.villtech.viewmodel

import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.ub.villtech.R
import com.dkmkknub.villtech.component.DetailPostVideo
import com.dkmkknub.villtech.model.ContentImageResponse
import com.dkmkknub.villtech.model.ContentVideoResponse
import com.dkmkknub.villtech.repository.firebase.FirebaseRepository
import com.dkmkknub.villtech.room.BookmarkedEntity
import com.dkmkknub.villtech.ui.theme.*
import com.dkmkknub.villtech.viewmodel.DetailPostViewModel
import com.dkmkknub.villtech.roomViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailPostScreen(navController: NavController, contentId: String) {
    val detailViewModel = getViewModel<DetailPostViewModel>()
    detailViewModel.checkIsFavorite(contentId)

    Scaffold(
        topBar = { DetailPostScreenTopBar(detailViewModel, navController, contentId) }
    ) {
        DetailPostContent(contentId = contentId, viewModel = detailViewModel)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun DetailPostScreenTopBar(
    viewModel: DetailPostViewModel,
    navController: NavController,
    contentId: String
) {
    TopAppBar(
        backgroundColor = GreenMint,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Btn Back
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(CornerSize(14.dp)))
                    .background(Light)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true, color = Dark),
                        onClick = {
                            navController.popBackStack()
                        }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Dark
                )
            }

            //Btn Star
            val scope = rememberCoroutineScope()
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(CornerSize(14.dp)))
                    .background(Light)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true, color = Dark),
                        onClick = {
                            viewModel.isFavorite.value = !viewModel.isFavorite.value

                            if (!viewModel.isFavorite.value) {
                                if (!viewModel.isBookmarkRemoved.value) {
                                    viewModel.isBookmarkRemoved.value = false
                                    scope.launch {
                                        roomViewModel.removeBookmarkContent(contentId)
                                    }

                                    viewModel.isBookmarkRemoved.value = true
                                }
                            } else {
                                if (!viewModel.isBookmarkAdded) {
                                    viewModel.isBookmarkAdded = false
                                    scope.launch {
                                        roomViewModel
                                            .addBookmarkedContent(BookmarkedEntity(contentId = contentId))
                                    }
                                    viewModel.isBookmarkAdded = true
                                }
                            }
                        }),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(targetState = viewModel.isFavorite.value) {
                    if (viewModel.isFavorite.value) {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = "Back",
                            tint = Dark
                        )
                    } else {
                        Icon(
                            modifier = Modifier.padding(8.dp),
                            imageVector = Icons.Default.StarOutline,
                            contentDescription = "Back",
                            tint = Dark
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun DetailPostContent(contentId: String, viewModel: DetailPostViewModel) {
    val firebaseRepository = get<FirebaseRepository>()
    val contentVideo: @Composable () -> Unit = {
        val data = remember { mutableStateOf<ContentVideoResponse?>(null) }

        firebaseRepository
            .database()
            .reference
            .child("content")
            .child("content_list")
            .child(contentId)
            .get()
            .addOnSuccessListener {
                //Pass the data into data class
                data.value = ContentVideoResponse(
                    content_id = it.child("content_id").value.toString(),
                    title = it.child("title").value.toString(),
                    author = it.child("author").value.toString(),
                    category = it.child("category").value.toString(),
                    media_url = it.child("media_url").value.toString(),
                    thumbnail_url = it.child("thumbnail_url").value.toString(),
                    post_date = it.child("post_date").value.toString(),
                    description = it.child("description").value.toString(),
                    commentCount = it.child("commentCount").value.toString(),
                )
            }

        if (data.value != null) {
            LazyColumn(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        text = data.value!!.title,
                        style = Typography.h2,
                        color = Dark
                    )
                }

                item {
                    DetailPostVideo(
                        uri = Uri.parse(data.value!!.media_url),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = data.value!!.author,
                        style = Typography.body1,
                        fontSize = 10.sp,
                        color = GreenMid
                    )
                    Text(
                        text = data.value!!.post_date,
                        style = Typography.body1,
                        fontSize = 10.sp,
                        color = Dark
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 32.dp),
                        text = data.value!!.description,
                        style = Typography.body1,
                        color = Dark
                    )
                }
            }
        }
    }
    val contentImage: @Composable () -> Unit = {
        val listOfUrl = remember { ArrayList<String>() }
        val data = remember { mutableStateOf<ContentImageResponse?>(null) }
        val pagerHeight = LocalConfiguration.current.screenWidthDp / 16 * 9

        firebaseRepository
            .database()
            .reference
            .child("content")
            .child("content_list")
            .child(contentId)
            .get()
            .addOnSuccessListener {
                //Get URL First
                it.child("media_url")
                    .children
                    .forEach { listOfUrl.add(it.value.toString()) }

                //Pass the data into data class
                data.value = ContentImageResponse(
                    content_id = it.child("content_id").value.toString(),
                    title = it.child("title").value.toString(),
                    author = it.child("author").value.toString(),
                    category = it.child("category").value.toString(),
                    media_url = listOfUrl,
                    thumbnail_url = it.child("thumbnail_url").value.toString(),
                    post_date = it.child("post_date").value.toString(),
                    description = it.child("description").value.toString(),
                    commentCount = it.child("commentCount").value.toString(),
                )
            }

        if (data.value != null) {
            LazyColumn(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        text = data.value!!.title,
                        style = Typography.h2,
                        color = Dark
                    )
                }

                item {
                    val state = rememberPagerState()
                    HorizontalPager(
                        modifier = Modifier
                            .clip(RoundedCornerShape(CornerSize(8.dp))),
                        count = listOfUrl.size, state = state
                    ) { index ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(pagerHeight.dp)
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(pagerHeight.dp),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .placeholder(R.drawable.ic_no_picture)
                                    .data(listOfUrl[index])
                                    .build(),
                                contentDescription = "Image",
                                contentScale = ContentScale.FillHeight
                            )
                        }

                    }
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp), contentAlignment = Alignment.Center){
                        HorizontalPagerIndicator(pagerState = state)
                    }
                }

                item {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = data.value!!.author,
                        style = Typography.body1,
                        fontSize = 10.sp,
                        color = GreenMid
                    )
                    Text(
                        text = data.value!!.post_date,
                        style = Typography.body1,
                        fontSize = 10.sp,
                        color = Dark
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 32.dp),
                        text = data.value!!.description,
                        style = Typography.body1,
                        color = Dark
                    )
                }
            }
        }
    }
    var content by remember { mutableStateOf<@Composable (() -> Unit)?>(null) }

    viewModel.checkMediaType(contentId) { type ->
        when (type) {
            "image" -> content = contentImage
            "video" -> content = contentVideo
        }
    }

    if (content != null) content!!()
}