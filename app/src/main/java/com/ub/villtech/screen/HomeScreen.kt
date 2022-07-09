package com.ub.villtech.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ub.villtech.component.AdminCard
import com.ub.villtech.component.ProductPreviewCard
import com.ub.villtech.component.StaggeredVerticalGrid
import com.ub.villtech.rootViewModel
import com.ub.villtech.ui.theme.GreenMint
import com.ub.villtech.ui.theme.Light
import com.ub.villtech.ui.theme.Typography
import com.ub.villtech.viewmodel.AdminHomeViewModel
import com.ub.villtech.viewmodel.UserHomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(navController: NavController) {
    if (rootViewModel.isLoginWithAdmin) {
        AdminHomeScreen()
    } else {
        UserHomeScreen()
    }
}

@Composable
private fun UserHomeScreen() {
    val viewModel = getViewModel<UserHomeViewModel>()
    viewModel.getContentList(
        onLoading = { viewModel.isLoading = true },
        count = 10,
        onSuccess = { viewModel.isLoading = false })
    val scrWidth = LocalConfiguration.current.screenWidthDp

    val state = rememberLazyListState()
    if (state.isScrollInProgress) {
        if (state.layoutInfo.visibleItemsInfo.any { it.key == "last_item" }) {
            viewModel.getContentList(
                onLoading = { viewModel.isLoading = true },
                count = 10,
                onSuccess = { viewModel.isLoading = false })
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = state,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            StaggeredVerticalGrid(columnCount = 2) {
                items(viewModel.contentList) { content ->
                    ProductPreviewCard(
                        contentWidth = (scrWidth / 2).dp,
                        contentData = content,
                        padding = 4.dp
                    )
                }
            }
        }

        item(key = "last_item") {
            Divider(
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
                color = GreenMint
            )
        }

        item {
            AnimatedVisibility(visible = viewModel.isLoading) {
                CircularProgressIndicator(strokeWidth = 3.dp, color = GreenMint)
            }
        }
    }
}

@Composable
private fun AdminHomeScreen() {
    val viewModel = getViewModel<AdminHomeViewModel>()
    val topBar: @Composable () -> Unit = {
        TopAppBar(
            modifier = Modifier,
            backgroundColor = Color.White,
            elevation = 4.dp
        ) {
            val topbarWidth = LocalConfiguration.current.screenWidthDp - 32
            val clickableAreaWidth = LocalConfiguration.current.screenWidthDp / 2
            val dividerLength = topbarWidth / 2

            Row(
                modifier = Modifier
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .width(clickableAreaWidth.dp)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true, color = Color.Black),
                            onClick = {
                                viewModel.selectState = AdminHomeScreenTopBarSelection.Admin
                            }
                        ),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Admin",
                            style = Typography.body1,
                            color = viewModel.adminColor
                        )
                        Divider(
                            modifier = Modifier.width(dividerLength.dp),
                            color = viewModel.adminColor,
                            thickness = 2.dp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .width(clickableAreaWidth.dp)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true, color = Color.Black),
                            onClick = {
                                viewModel.selectState = AdminHomeScreenTopBarSelection.Posting
                            }
                        ),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column(
                        modifier = Modifier.padding(end = 16.dp, bottom = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Posting",
                            style = Typography.body1,
                            color = viewModel.postingColor
                        )
                        Divider(
                            modifier = Modifier.width(dividerLength.dp),
                            color = viewModel.postingColor,
                            thickness = 2.dp
                        )
                    }
                }
            }
        }
    }

    val runListener: () -> Unit = {
        when (viewModel.selectState) {
            AdminHomeScreenTopBarSelection.Admin -> {
                viewModel.adminColor = GreenMint
                viewModel.postingColor = Color.Gray
                viewModel.content = { AdminAdminHomeScreen() }
            }

            AdminHomeScreenTopBarSelection.Posting -> {
                viewModel.adminColor = Color.Gray
                viewModel.postingColor = GreenMint
                viewModel.content = { PostingAdminHomeScreen() }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = topBar) {
        runListener()
        viewModel.content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AdminAdminHomeScreen() {
    val viewModel = getViewModel<AdminHomeViewModel>()
    viewModel.getAdminList()
    val cardWidth = LocalConfiguration.current.screenWidthDp / 3

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        item {
            AnimatedContent(targetState = viewModel.isAdminLoaded) { isAdminLoaded ->
                if (isAdminLoaded) {
                    if (viewModel.listOfAdmin.isNotEmpty()) {
                        StaggeredVerticalGrid(maxColumnWidth = cardWidth.dp) {
                            items(viewModel.listOfAdmin) {
                                Box(
                                    modifier = Modifier.padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    AdminCard(
                                        contentWidth = cardWidth.dp,
                                        adminInfo = it
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = GreenMint)
                    }
                }
            }
        }
    }
}

@Composable
fun PostingAdminHomeScreen() {
    val viewModel = getViewModel<AdminHomeViewModel>()

    val profilePictureWidth = LocalConfiguration.current.screenWidthDp * 1 / 7
    val textFieldWidth = LocalConfiguration.current.screenWidthDp * 6/7


}

enum class AdminHomeScreenTopBarSelection {
    Admin,
    Posting
}