package com.dkmkknub.villtech.screen

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dkmkknub.villtech.component.PreviewCardImage
import com.dkmkknub.villtech.component.PreviewCardVideo
import com.dkmkknub.villtech.component.SearchInputField
import com.dkmkknub.villtech.component.StaggeredVerticalGrid
import com.dkmkknub.villtech.ui.theme.Dark
import com.dkmkknub.villtech.ui.theme.GreenMint
import com.dkmkknub.villtech.ui.theme.Light
import com.dkmkknub.villtech.ui.theme.Typography
import com.dkmkknub.villtech.navigation.NavigationRoute
import com.dkmkknub.villtech.viewmodel.SearchViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel = getViewModel<SearchViewModel>()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        SearchScreenTopSection(viewModel = viewModel)
        
        when(viewModel.searchState.value.length){
            0 -> SearchScreenContentDefault()
            1 -> SearchScreenBelowSectionFirstLetter(viewModel = viewModel, navController = navController)
            else -> SearchScreenBelowSectionSecondLetterAndMore(viewModel = viewModel, navController = navController)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchScreenTopSection(viewModel: SearchViewModel) {
    val topMenuCard: @Composable (String, Int, () -> Unit) -> Unit =
        { keyword, menuIndex, onClick ->
            var background by remember { mutableStateOf(Light) }
            var textColor by remember { mutableStateOf(GreenMint) }

            if (viewModel.topMenuIndexSelected == menuIndex) {
                background = GreenMint
                textColor = Light
            } else {
                background = Light
                textColor = GreenMint
            }

            AnimatedContent(
                targetState = background,
                transitionSpec = {
                    fadeIn(animationSpec = tween(600)) with fadeOut(
                        animationSpec = tween(600)
                    )
                }) { backgroundAnimated ->
                AnimatedContent(
                    targetState = textColor,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(600)) with fadeOut(
                            animationSpec = tween(600)
                        )
                    }) { textColorAnimated ->
                    Card(
                        modifier = Modifier
                            .clip(RoundedCornerShape(CornerSize(Int.MAX_VALUE.dp)))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = true, color = Color.Black),
                                onClick = {
                                    onClick()
                                    viewModel.topMenuIndexSelected = menuIndex
                                }
                            ),
                        backgroundColor = backgroundAnimated,
                        shape = RoundedCornerShape(CornerSize(Int.MAX_VALUE.dp)),
                        elevation = 4.dp,
                        border = BorderStroke(width = 2.dp, color = GreenMint)
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            ), text = keyword, style = Typography.body2, color = textColorAnimated
                        )
                    }
                }
            }
        }

    val topMenu: @Composable () -> Unit = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            topMenuCard("Semua", 0) {
                if (viewModel.searchState.value.length == 1) {
                    viewModel.listOfResultWithImage = null
                    viewModel.listOfResultWithVideo = null
                    viewModel.searchOnFirstLetterAll()
                }
                else if (viewModel.searchState.value.length > 1){
                    viewModel.searchSecondLetterAndMoreAll()
                }
            }
            topMenuCard("Infografis", 1) {
                if (viewModel.searchState.value.length == 1) {
                    viewModel.searchOnFirstLetterInfografis()
                }
                else if (viewModel.searchState.value.length > 1){
                    viewModel.searchSecondLetterAndMoreInfografis()
                }
            }
            topMenuCard("Video", 2) {
                if (viewModel.searchState.value.length == 1) {
                    viewModel.listOfResultWithImage = null
                    viewModel.listOfResultWithVideo = null
                    viewModel.searchOnFirstLetterVideo()
                }
                else if (viewModel.searchState.value.length > 1){
                    viewModel.listOfResultWithImageOffline = null
                    viewModel.listOfResultWithVideoOffline = null
                    viewModel.searchSecondLetterAndMoreVideo()
                }
            }
        }
    }
    val searchField: @Composable () -> Unit = {
        SearchInputField(
            modifier = Modifier.fillMaxWidth(),
            valueState = viewModel.searchState,
            onSearch = {
                if (it.isEmpty()) {
                    viewModel.listOfResultWithImage = null
                    viewModel.listOfResultWithVideo = null
                    viewModel.listOfResultWithImageOffline = null
                    viewModel.listOfResultWithVideoOffline = null
                }
                else if (it.length == 1) {
                    when (viewModel.topMenuIndexSelected) {
                        0 -> {
                            viewModel.searchOnFirstLetterAll()
                        }
                        1 -> {
                            viewModel.searchOnFirstLetterInfografis()
                        }
                        2 -> {
                            viewModel.searchOnFirstLetterVideo()
                        }
                    }
                }
                else if (it.length > 1) {
                    when (viewModel.topMenuIndexSelected) {
                        0 -> {
                            viewModel.searchSecondLetterAndMoreAll()
                        }
                        1 -> {
                            viewModel.searchSecondLetterAndMoreInfografis()
                        }
                        2 -> {
                            viewModel.searchSecondLetterAndMoreVideo()
                        }
                    }
                }

                if(!viewModel.listOfResultWithImage.isNullOrEmpty()){
                    Log.e("MASUK", viewModel.listOfResultWithImage!!.toString())
                }
            },
            placeHolderText = "Cari Tentang Desa Sidomulyo",
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Dark
                )
            },
            shape = RoundedCornerShape(CornerSize(12.dp))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topMenu()
        Spacer(modifier = Modifier.height(16.dp))
        searchField()
    }
}

@Composable
private fun SearchScreenBelowSectionFirstLetter(viewModel: SearchViewModel, navController: NavController) {
    val contentWidth = LocalConfiguration.current.screenWidthDp / 2

    LazyColumn {
        item {
            StaggeredVerticalGrid(columnCount = 2) {
                if(!viewModel.listOfResultWithImage.isNullOrEmpty()){
                    items(viewModel.listOfResultWithImage!!) {
                        PreviewCardImage(
                            contentWidth = contentWidth.dp,
                            contentData = it,
                            onClick = {
                                navController.navigate("${NavigationRoute.DetailPostScreen.name}/${it.content_id}")
                            }
                        )
                    }
                }

                if(!viewModel.listOfResultWithVideo.isNullOrEmpty()){
                    items(viewModel.listOfResultWithVideo!!) {
                        PreviewCardVideo(
                            contentWidth = contentWidth.dp,
                            contentData = it,
                            onClick = {
                                navController.navigate("${NavigationRoute.DetailPostScreen.name}/${it.content_id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchScreenBelowSectionSecondLetterAndMore(viewModel: SearchViewModel, navController: NavController) {
    val contentWidth = LocalConfiguration.current.screenWidthDp / 2

    LazyColumn {
        item {
            StaggeredVerticalGrid(columnCount = 2) {
                if(!viewModel.listOfResultWithImageOffline.isNullOrEmpty()){
                    items(viewModel.listOfResultWithImageOffline!!) {
                        PreviewCardImage(
                            contentWidth = contentWidth.dp,
                            contentData = it,
                            onClick = {
                                navController.navigate("${NavigationRoute.DetailPostScreen.name}/${it.content_id}")
                            }
                        )
                    }
                }

                if(!viewModel.listOfResultWithVideoOffline.isNullOrEmpty()){
                    items(viewModel.listOfResultWithVideoOffline!!) {
                        PreviewCardVideo(
                            contentWidth = contentWidth.dp,
                            contentData = it,
                            onClick = {
                                navController.navigate("${NavigationRoute.DetailPostScreen.name}/${it.content_id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchScreenContentDefault() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Silahkan Masukkan Kata Kunci Pencarian",
            style = Typography.h1,
            color = Color.Gray
        )
    }
}