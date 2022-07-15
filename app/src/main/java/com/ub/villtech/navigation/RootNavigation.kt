package com.ub.villtech.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.ub.villtech.screen.*
import com.ub.villtech.viewmodel.BottomNavigationViewModel
import com.ub.villtech.viewmodel.DetailPostScreen
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(navController: NavHostController) {
    val rootViewModel = getViewModel<RootViewModel>()
    val bottomNavigationViewModel = getViewModel<BottomNavigationViewModel>()

    AnimatedNavHost(
        navController = navController,
        startDestination = NavigationRoute.SplashScreen.name
    ) {
        composable(route = NavigationRoute.SplashScreen.name) {
            SplashScreen(navController = navController)
            rootViewModel.isBottomNavigationEnabled = false
        }
        composable(route = NavigationRoute.OnboardScreen.name) {
            OnboardScreen(navController = navController)
            rootViewModel.isBottomNavigationEnabled = false
        }
        composable(route = NavigationRoute.AdminLoginScreen.name) {
            LoginScreen(navController = navController)
            rootViewModel.isBottomNavigationEnabled = false
        }
        composable(route = NavigationRoute.HomeScreen.name) {
            HomeScreen(navController = navController)
            bottomNavigationViewModel.selectState = NavigationRoute.HomeScreen
            rootViewModel.isBottomNavigationEnabled = true
        }
        composable(route = NavigationRoute.SearchScreen.name) {
            SearchScreen(navController = navController)
            bottomNavigationViewModel.selectState = NavigationRoute.SearchScreen
            rootViewModel.isBottomNavigationEnabled = true
        }
        composable(route = NavigationRoute.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
            bottomNavigationViewModel.selectState = NavigationRoute.FavoriteScreen
            rootViewModel.isBottomNavigationEnabled = true
        }
        composable(route = NavigationRoute.AboutScreen.name) {
            AboutScreen()
            bottomNavigationViewModel.selectState = NavigationRoute.AboutScreen
            rootViewModel.isBottomNavigationEnabled = true
        }
        composable(
            route = "${NavigationRoute.DetailPostScreen.name}/{content_id}",
            arguments = listOf(navArgument("content_id"){
                type = NavType.StringType
            })
        ) {
            it.arguments?.getString("content_id").let {
                if(it!=null){
                    rootViewModel.isBottomNavigationEnabled = false
                    DetailPostScreen(navController, it)
                }
            }
        }
    }
}