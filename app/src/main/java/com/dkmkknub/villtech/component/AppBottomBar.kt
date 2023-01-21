package com.dkmkknub.villtech.component

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
import com.dkmkknub.villtech.ui.theme.GreenLight
import com.dkmkknub.villtech.ui.theme.Light
import com.ub.villtech.R
import com.dkmkknub.villtech.navigation.NavigationRoute
import com.dkmkknub.villtech.rootViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun AppBottomBar(currentRoute: String, onItemClicked: (route: String) -> Unit) {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(CornerSize(14.dp))),
        backgroundColor = Light,
        elevation = 4.dp
    ) {
        AppBottomBarItem.values().forEach {
            BottomNavigationContent(
                onClick = onItemClicked,
                botnavItem = it,
                isSelected = it.route == currentRoute
            )
        }
    }
}

@Composable
private fun BottomNavigationContent(
    onClick: (route: String) -> Unit,
    botnavItem: AppBottomBarItem,
    isSelected: Boolean
) {
    val scrWidth = LocalConfiguration.current.screenWidthDp
    var targetWidth by remember { mutableStateOf(scrWidth / 5.0) }
    targetWidth = when {
        isSelected -> scrWidth / 3.5
        else -> scrWidth / 7.0
    }
    val animatedWidth by animateDpAsState(targetValue = targetWidth.dp)

    //Root box that accommodate all of our item as a single composable
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp), contentAlignment = Alignment.Center
    ) {
        //One Box again, to separate our background. Because we want to our background visibility animated
        AnimatedVisibility(visible = isSelected) {
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
            IconButton(onClick = { onClick(botnavItem.route) }) {
                Icon(
                    painter = painterResource(
                        id = when {
                            isSelected -> botnavItem.selectedIconId
                            else -> botnavItem.unselectedIconId
                        }
                    ),
                    contentDescription = "Icon",
                    tint = Color.Unspecified
                )
            }

            AnimatedVisibility(visible = isSelected) {
                Text(text = botnavItem.word, color = Color.Black, fontSize = 12.sp)
            }
        }
    }
}

private data class NavbarItem(
    val iconId: Int,
    val name: String,
    val enumSelection: NavigationRoute,
    val onClick: () -> Unit
)

enum class AppBottomBarItem(
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val word: String,
    val route: String
) {
    Home(
        R.drawable.ic_botnavbar_home_click,
        R.drawable.ic_botnavbar_home_unclick,
        "Beranda",
        NavigationRoute.HomeScreen.name,
    ),
    Search(
        R.drawable.ic_botnavbar_search_click,
        R.drawable.ic_botnavbar_search_unclick,
        "Jelajah",
        NavigationRoute.SearchScreen.name
    ),
    Favorite(
        R.drawable.ic_botnavbar_favorite_click,
        R.drawable.ic_botnavbar_favorite_unclick,
        "Favorit",
        NavigationRoute.FavoriteScreen.name
    ),
    About(
        R.drawable.ic_botnavbar_about_click,
        R.drawable.ic_botnavbar_about_unclick,
        "Tentang",
        NavigationRoute.AboutScreen.name
    )
}
