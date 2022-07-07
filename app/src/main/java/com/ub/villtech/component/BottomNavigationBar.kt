package com.ub.villtech.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ub.villtech.R
import com.ub.villtech.navigation.NavigationRoute
import com.ub.villtech.ui.theme.*
import com.ub.villtech.viewmodel.BottomNavigationViewModel
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val viewModel = getViewModel<BottomNavigationViewModel>()
    val rootViewModel = getViewModel<RootViewModel>()

    if (rootViewModel.isBottomNavigationEnabled) {
        val listOfIconId = ArrayList<MutableState<Int>>()
        listOfIconId.add(remember { mutableStateOf(R.drawable.ic_botnavbar_home_click) })
        listOfIconId.add(remember { mutableStateOf(R.drawable.ic_botnavbar_search_unclick) })
        listOfIconId.add(remember { mutableStateOf(R.drawable.ic_botnavbar_favorite_unclick) })
        listOfIconId.add(remember { mutableStateOf(R.drawable.ic_botnavbar_about_unclick) })
        val listOfVisibleState = ArrayList<MutableState<Boolean>>()
        repeat(4) {
            listOfVisibleState.add(remember { mutableStateOf(false) })
        }
        val listOfNavbarItem = listOf(
            NavbarItem(
                iconId = listOfIconId[0].value,
                name = "Beranda",
                enumSelection = NavigationRoute.HomeScreen,
                onClick = {
                    if (viewModel.selectState != NavigationRoute.HomeScreen) {
                        navController.popBackStack()
                        navController.navigate(NavigationRoute.HomeScreen.name)
                    }
                }
            ),
            NavbarItem(
                iconId = listOfIconId[1].value,
                name = "Jelajah",
                enumSelection = NavigationRoute.SearchScreen,
                onClick = {
                    if (viewModel.selectState != NavigationRoute.SearchScreen) {
                        navController.popBackStack()
                        navController.navigate(NavigationRoute.SearchScreen.name)
                    }
                }
            ),
            NavbarItem(
                iconId = listOfIconId[2].value,
                name = "Favorit",
                enumSelection = NavigationRoute.FavoriteScreen,
                onClick = {
                    if (viewModel.selectState != NavigationRoute.FavoriteScreen) {
                        navController.popBackStack()
                        navController.navigate(NavigationRoute.FavoriteScreen.name)
                    }
                }
            ),
            NavbarItem(
                iconId = listOfIconId[3].value,
                name = "Tentang",
                enumSelection = NavigationRoute.AboutScreen,
                onClick = {
                    if (viewModel.selectState != NavigationRoute.AboutScreen) {
                        navController.popBackStack()
                        navController.navigate(NavigationRoute.AboutScreen.name)
                    }
                }
            )
        )

        runBottomNavigationListener(
            navController = navController,
            viewModel = viewModel,
            listOfIconId = listOfIconId,
            listOfVisibleState = listOfVisibleState,
            listOfNavbarItem = listOfNavbarItem
        )


        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
                .clip(RoundedCornerShape(CornerSize(14.dp))),
            backgroundColor = Light,
            elevation = 4.dp
        ) {
            listOfNavbarItem.forEachIndexed { index, navbarItem ->
                BottomNavigationContent(
                    viewModel = viewModel,
                    botnavItem = navbarItem,
                    visibleState = listOfVisibleState[index],
                    onClick = { navbarItem.onClick() }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationContent(
    onClick: () -> Unit,
    viewModel: BottomNavigationViewModel,
    botnavItem: NavbarItem,
    visibleState: MutableState<Boolean>
) {
    val scrWidth = LocalConfiguration.current.screenWidthDp
    var targetWidth by remember { mutableStateOf(scrWidth / 5.0) }
    when (viewModel.selectState) {
        botnavItem.enumSelection -> targetWidth = scrWidth / 3.5
        else -> targetWidth = scrWidth / 7.0
    }
    val animatedWidth by animateDpAsState(targetValue = targetWidth.dp)

    //Root box that accommodate all of our item as a single composable
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp), contentAlignment = Alignment.Center
    ) {
        //One Box again, to separate our background. Because we want to our background visibility animated
        AnimatedVisibility(visible = visibleState.value) {
            Box(
                modifier = Modifier
                    .width(animatedWidth)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(CornerSize(14.dp)))
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(GreenLight, Light)
                        )
                    )
            )
        }

        //This row used for our bottom navigation items
        Row(
            modifier = Modifier
                .width(animatedWidth)
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { onClick() }) {
                Icon(
                    painter = painterResource(id = botnavItem.iconId),
                    contentDescription = "Icon",
                    tint = Color.Unspecified
                )
            }

            AnimatedVisibility(visible = visibleState.value) {
                Text(text = botnavItem.name, color = Color.Black, fontSize = 12.sp)
            }
        }
    }
}

private fun runBottomNavigationListener(
    navController: NavController,
    viewModel: BottomNavigationViewModel,
    listOfIconId: ArrayList<MutableState<Int>>,
    listOfVisibleState: ArrayList<MutableState<Boolean>>,
    listOfNavbarItem: List<NavbarItem>
) {
    if (viewModel.selectState == NavigationRoute.HomeScreen) {
        listOfIconId[0].value = R.drawable.ic_botnavbar_home_click
        listOfVisibleState[0].value = true
    } else {
        listOfIconId[0].value = R.drawable.ic_botnavbar_home_unclick
        listOfVisibleState[0].value = false
    }

    if (viewModel.selectState == NavigationRoute.SearchScreen) {
        listOfIconId[1].value = R.drawable.ic_botnavbar_search_click
        listOfVisibleState[1].value = true
    } else {
        listOfIconId[1].value = R.drawable.ic_botnavbar_search_unclick
        listOfVisibleState[1].value = false
    }

    if (viewModel.selectState == NavigationRoute.FavoriteScreen) {
        listOfIconId[2].value = R.drawable.ic_botnavbar_favorite_click
        listOfVisibleState[2].value = true
    } else {
        listOfIconId[2].value = R.drawable.ic_botnavbar_favorite_unclick
        listOfVisibleState[2].value = false
    }

    if (viewModel.selectState == NavigationRoute.AboutScreen) {
        listOfIconId[3].value = R.drawable.ic_botnavbar_about_click
        listOfVisibleState[3].value = true
    } else {
        listOfIconId[3].value = R.drawable.ic_botnavbar_about_unclick
        listOfVisibleState[3].value = false
    }
}

private data class NavbarItem(
    val iconId: Int,
    val name: String,
    val enumSelection: NavigationRoute,
    val onClick: () -> Unit
)
