package com.ub.villtech.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.ub.villtech.screen.SearchScreen
import com.ub.villtech.screen.LoginScreen
import com.ub.villtech.screen.HomeScreen
import com.ub.villtech.screen.OnboardScreen
import com.ub.villtech.screen.SplashScreen
import com.ub.villtech.viewmodel.BottomNavigationViewModel
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(navController: NavHostController) {
    val rootViewModel = getViewModel<RootViewModel>()
    val bottomNavigationViewModel = getViewModel<BottomNavigationViewModel>()

    AnimatedNavHost(navController = navController, startDestination = NavigationRoute.SplashScreen.name){
        composable(route = NavigationRoute.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(route = NavigationRoute.OnboardScreen.name){
            OnboardScreen(navController = navController)
        }
        composable(route = NavigationRoute.AdminLoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(route = NavigationRoute.HomeScreen.name){
            rootViewModel.isBottomNavigationEnabled = true
            HomeScreen(navController = navController)
            bottomNavigationViewModel.selectState = NavigationRoute.HomeScreen
        }
        composable(route = NavigationRoute.SearchScreen.name){
            rootViewModel.isBottomNavigationEnabled = true
            SearchScreen(navController = navController)
            bottomNavigationViewModel.selectState = NavigationRoute.SearchScreen
        }
        composable(route = NavigationRoute.FavoriteScreen.name){
            rootViewModel.isBottomNavigationEnabled = true
            bottomNavigationViewModel.selectState = NavigationRoute.FavoriteScreen
        }
        composable(route = NavigationRoute.AboutScreen.name){
            rootViewModel.isBottomNavigationEnabled = true
            bottomNavigationViewModel.selectState = NavigationRoute.AboutScreen
        }
    }
}