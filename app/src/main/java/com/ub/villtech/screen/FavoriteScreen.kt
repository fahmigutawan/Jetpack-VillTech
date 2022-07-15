package com.ub.villtech.screen

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
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import com.ub.villtech.component.*
import com.ub.villtech.navigation.NavigationRoute
import com.ub.villtech.room.BookmarkedEntity
import com.ub.villtech.roomViewModel
import com.ub.villtech.ui.theme.Dark
import com.ub.villtech.ui.theme.GreenMint
import com.ub.villtech.ui.theme.Light
import com.ub.villtech.ui.theme.Typography
import com.ub.villtech.viewmodel.FavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun FavoriteScreen(navController: NavController) {
    val favViewModel = getViewModel<FavoriteViewModel>()
    favViewModel.resetState()

    LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        item {
            FavoriteScreenTopSection(viewModel = favViewModel)
        }

        item {
            FavoriteScreenBelowSection(viewModel = favViewModel, navController = navController)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FavoriteScreenTopSection(viewModel: FavoriteViewModel) {
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
    when (viewModel.topMenuSelected) {
        FavoriteScreenTopMenuSelection.All -> viewModel.topMenuIndexSelected = 0

        FavoriteScreenTopMenuSelection.Infographic -> viewModel.topMenuIndexSelected = 1

        FavoriteScreenTopMenuSelection.Video -> viewModel.topMenuIndexSelected = 2
    }

    val topMenu: @Composable () -> Unit = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            topMenuCard("Semua", 0) {
                Log.e("MASUK", "SINI")
            }
            topMenuCard("Infografis", 1) {

            }
            topMenuCard("Video", 2) {

            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topMenu()
    }
}

@Composable
private fun FavoriteScreenBelowSection(viewModel: FavoriteViewModel, navController: NavController) {
    when (viewModel.topMenuIndexSelected) {
        0 -> FavoriteScreenBelowSectionAll(viewModel = viewModel, navController = navController)
        1 -> FavoriteScreenBelowSectionInfografis(
            viewModel = viewModel,
            navController = navController
        )
        2 -> FavoriteScreenBelowSectionVideo(viewModel = viewModel, navController = navController)
    }
}

@Composable
private fun FavoriteScreenBelowSectionAll(
    viewModel: FavoriteViewModel,
    navController: NavController
) {
    val cardWidth = LocalConfiguration.current.screenWidthDp / 2

    if (!viewModel.isAllContentLoaded) {
        LaunchedEffect(true) {
            roomViewModel
                .getListOfBookmarkedContent().collect { list ->
                    list.forEach {
                        viewModel.getContentAll(
                            contentId = it.contentId,
                            onSuccess = {
                                viewModel.allCategorySuccessLoadedCount++
                                if (viewModel.allCategorySuccessLoadedCount == list.size)
                                    viewModel.isAllContentLoaded = true
                            }
                        )
                    }
                }
        }
    }

    if (viewModel.isAllContentLoaded) {
        StaggeredVerticalGrid(columnCount = 2) {
            if (viewModel.listOfFavoriteContentAllWithImage.isNotEmpty()) {
                items(viewModel.listOfFavoriteContentAllWithImage) {
                    PreviewCardImage(
                        contentWidth = cardWidth.dp,
                        contentData = it,
                        onClick = {
                            navController.navigate("${NavigationRoute.DetailPostScreen}/${it.content_id}")
                        })
                }
            }

            if (viewModel.listOfFavoriteContentAllWithVideo.isNotEmpty()) {
                items(viewModel.listOfFavoriteContentAllWithVideo) {
                    PreviewCardVideo(
                        contentWidth = cardWidth.dp,
                        contentData = it,
                        onClick = {
                            navController.navigate("${NavigationRoute.DetailPostScreen}/${it.content_id}")
                        })
                }
            }
        }
    } else {
        FavoriteScreenBelowEmpty()
    }
}

@Composable
private fun FavoriteScreenBelowSectionInfografis(
    viewModel: FavoriteViewModel,
    navController: NavController
) {
    val cardWidth = LocalConfiguration.current.screenWidthDp / 2

    if (!viewModel.isInfografisContentLoaded) {
        LaunchedEffect(true) {
            roomViewModel
                .getListOfBookmarkedContent().collect { list ->
                    list.forEach {
                        viewModel.getContentInfografis(
                            contentId = it.contentId,
                            onSuccess = {
                                viewModel.infografisSuccessLoadedCount++
                                if (viewModel.infografisSuccessLoadedCount == list.size)
                                    viewModel.isInfografisContentLoaded = true
                            }
                        )
                    }
                }
        }
    }

    if (viewModel.isInfografisContentLoaded) {
        StaggeredVerticalGrid(columnCount = 2) {
            if (viewModel.listOfFavoriteContentInfografisWithImage.isNotEmpty()) {
                items(viewModel.listOfFavoriteContentAllWithImage) {
                    PreviewCardImage(
                        contentWidth = cardWidth.dp,
                        contentData = it,
                        onClick = {
                            navController.navigate("${NavigationRoute.DetailPostScreen}/${it.content_id}")
                        })
                }
            }

            if (viewModel.listOfFavoriteContentInfografisWithVideo.isNotEmpty()) {
                items(viewModel.listOfFavoriteContentAllWithVideo) {
                    PreviewCardVideo(
                        contentWidth = cardWidth.dp,
                        contentData = it,
                        onClick = {
                            navController.navigate("${NavigationRoute.DetailPostScreen}/${it.content_id}")
                        })
                }
            }
        }
    } else {
        FavoriteScreenBelowEmpty()
    }
}

@Composable
private fun FavoriteScreenBelowSectionVideo(
    viewModel: FavoriteViewModel,
    navController: NavController
) {
    val cardWidth = LocalConfiguration.current.screenWidthDp / 2

    if (!viewModel.isVideoContentLoaded) {
        LaunchedEffect(true) {
            roomViewModel
                .getListOfBookmarkedContent().collect { list ->
                    list.forEach {
                        viewModel.getContentVideo(
                            contentId = it.contentId,
                            onSuccess = {
                                viewModel.videoSuccessLoadedCount++
                                if (viewModel.videoSuccessLoadedCount == list.size)
                                    viewModel.isVideoContentLoaded = true
                            }
                        )
                    }
                }
        }
    }

    if (viewModel.isVideoContentLoaded) {
        StaggeredVerticalGrid(columnCount = 2) {
            if (viewModel.listOfFavoriteContentVideoWithImage.isNotEmpty()) {
                items(viewModel.listOfFavoriteContentVideoWithImage) {
                    PreviewCardImage(
                        contentWidth = cardWidth.dp,
                        contentData = it,
                        onClick = {
                            navController.navigate("${NavigationRoute.DetailPostScreen}/${it.content_id}")
                        })
                }
            }

            if (viewModel.listOfFavoriteContentVideoWithVideo.isNotEmpty()) {
                items(viewModel.listOfFavoriteContentVideoWithVideo) {
                    PreviewCardVideo(
                        contentWidth = cardWidth.dp,
                        contentData = it,
                        onClick = {
                            navController.navigate("${NavigationRoute.DetailPostScreen}/${it.content_id}")
                        })
                }
            }
        }
    } else {
        FavoriteScreenBelowEmpty()
    }
}

@Composable
private fun FavoriteScreenBelowEmpty() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Belum Ada Konten Favorit",
            style = Typography.h1,
            color = Color.Gray
        )
    }
}

enum class FavoriteScreenTopMenuSelection {
    All, Infographic, Video
}