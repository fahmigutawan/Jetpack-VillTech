package com.ub.villtech.screen.user

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ub.villtech.component.StaggeredVerticalGrid
import com.ub.villtech.component.TextInputField
import com.ub.villtech.ui.theme.*
import com.ub.villtech.viewmodel.SearchViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SearchScreen(navController: NavController) {
    val viewModel = getViewModel<SearchViewModel>()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchScreenTopSection(viewModel = viewModel)
        SearchScreenBelowSection(viewModel = viewModel)
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
    when (viewModel.topMenuSelected) {
        SearchScreenTopMenuSelection.All -> viewModel.topMenuIndexSelected = 0

        SearchScreenTopMenuSelection.Infographic -> viewModel.topMenuIndexSelected = 1

        SearchScreenTopMenuSelection.Video -> viewModel.topMenuIndexSelected = 2
    }

    val topMenu: @Composable () -> Unit = {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            topMenuCard("Semua", 0, {})
            topMenuCard("Infografis", 1, {})
            topMenuCard("Video", 2, {})
        }
    }
    val searchField: @Composable () -> Unit = {
        TextInputField(
            modifier = Modifier.fillMaxWidth(),
            valueState = viewModel.searchState,
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
private fun SearchScreenBelowSection(viewModel: SearchViewModel) {

}

enum class SearchScreenTopMenuSelection {
    All, Infographic, Video
}