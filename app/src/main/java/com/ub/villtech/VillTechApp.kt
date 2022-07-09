package com.ub.villtech

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ub.villtech.component.BottomNavigationBar
import com.ub.villtech.component.GreenSnackbar
import com.ub.villtech.navigation.RootNavigation
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.getViewModel

lateinit var scaffoldState: ScaffoldState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VillTechApp() {
    scaffoldState = rememberScaffoldState()
    val rootViewModel = getViewModel<RootViewModel>()
    rootViewModel.contentHeight = LocalConfiguration.current.screenHeightDp
    val navController = rememberAnimatedNavController()

    Box(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            snackbarHost = { GreenSnackbar(hostState = it) },
            bottomBar = { BottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()).fillMaxSize()) {
                RootNavigation(navController = navController)
            }
        }
    }
}