package com.ub.villtech

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ub.villtech.component.BottomNavigationBar
import com.ub.villtech.component.GreenSnackbar
import com.ub.villtech.navigation.RootNavigation
import com.ub.villtech.screen.admin.AdminLoginScreen
import com.ub.villtech.screen.user.OnboardScreen
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.getViewModel

lateinit var scaffoldState: ScaffoldState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VillTechApp() {
    scaffoldState = rememberScaffoldState()
    val navController = rememberAnimatedNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = { GreenSnackbar(hostState = it) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            RootNavigation(navController = navController)
        }
    }
}