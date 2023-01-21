package com.dkmkknub.villtech

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.dkmkknub.villtech.component.AppBottomBar
import com.dkmkknub.villtech.component.GreenSnackbar
import com.dkmkknub.villtech.navigation.NavigationRoute
import com.dkmkknub.villtech.screen.*
import com.dkmkknub.villtech.viewmodel.RootViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.ub.villtech.viewmodel.DetailPostScreen
import org.koin.androidx.compose.getViewModel

lateinit var scaffoldState: ScaffoldState

@Composable
fun VillTechApp(navController: NavHostController) {
    scaffoldState = rememberScaffoldState()
    val rootViewModel = getViewModel<RootViewModel>()
    rootViewModel.contentHeight = LocalConfiguration.current.screenHeightDp

    navController.addOnDestinationChangedListener { _, destination, _ ->
        destination.route?.let { route ->
            rootViewModel.bottomBarState.value = route
            when (route) {
                NavigationRoute.HomeScreen.name -> {
                    rootViewModel.showBottomBar = true
                }
                NavigationRoute.SearchScreen.name -> {
                    rootViewModel.showBottomBar = true
                }
                NavigationRoute.FavoriteScreen.name -> {
                    rootViewModel.showBottomBar = true
                }
                NavigationRoute.AboutScreen.name -> {
                    rootViewModel.showBottomBar = true
                }
                else -> {
                    rootViewModel.showBottomBar = false
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            snackbarHost = { GreenSnackbar(hostState = it) },
            bottomBar = {
                if (rootViewModel.showBottomBar) {
                    AppBottomBar(
                        currentRoute = rootViewModel.bottomBarState.value,
                        onItemClicked = { route ->
                            navController.navigate(route = route)
                        }
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(bottom = innerPadding.calculateBottomPadding())
                    .fillMaxSize()
            ) {
                VilltechNavHost(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun VilltechNavHost(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationRoute.SplashScreen.name
    ) {
        composable(route = NavigationRoute.SplashScreen.name) {
            SplashScreen(
                navigateToOnboardScreen = {
                    navController.navigate(route = NavigationRoute.OnboardScreen.name) {
                        popUpTo(route = NavigationRoute.SplashScreen.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = NavigationRoute.OnboardScreen.name) {
            OnboardScreen(navController = navController)
        }
        composable(route = NavigationRoute.AdminLoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(route = NavigationRoute.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(route = NavigationRoute.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
        composable(route = NavigationRoute.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
        }
        composable(route = NavigationRoute.AboutScreen.name) {
            AboutScreen()
        }
        composable(
            route = "${NavigationRoute.DetailPostScreen.name}/{content_id}",
            arguments = listOf(navArgument("content_id") {
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("content_id").let { content_id ->
                if (content_id != null) {
                    DetailPostScreen(navController, content_id)
                }
            }
        }
    }
}