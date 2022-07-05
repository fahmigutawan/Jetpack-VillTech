package com.ub.villtech.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.ub.villtech.component.BottomNavigationSelection
import com.ub.villtech.screen.admin.AdminLoginScreen
import com.ub.villtech.screen.user.OnboardScreen
import com.ub.villtech.screen.user.SplashScreen
import com.ub.villtech.viewmodel.BottomNavigationViewModel
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(navController: NavHostController) {
    val rootViewModel = getViewModel<RootViewModel>()
    val bottomNavigationViewModel = getViewModel<BottomNavigationViewModel>()

    AnimatedNavHost(navController = navController, startDestination = BottomNavigationSelection.Home.name){
        composable(route = BottomNavigationSelection.Home.name){
            rootViewModel.isBottomNavigationEnabled = true
            bottomNavigationViewModel.selectState = BottomNavigationSelection.Home
        }
        composable(route = BottomNavigationSelection.Search.name){
            rootViewModel.isBottomNavigationEnabled = true
            bottomNavigationViewModel.selectState = BottomNavigationSelection.Home
        }
        composable(route = BottomNavigationSelection.Favorite.name){
            rootViewModel.isBottomNavigationEnabled = true
            bottomNavigationViewModel.selectState = BottomNavigationSelection.Home
        }
        composable(route = BottomNavigationSelection.About.name){
            rootViewModel.isBottomNavigationEnabled = true
            bottomNavigationViewModel.selectState = BottomNavigationSelection.Home
        }
    }
}