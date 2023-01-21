package com.dkmkknub.villtech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.dkmkknub.villtech.navigation.NavigationRoute
import com.dkmkknub.villtech.ui.theme.VillTechTheme
import com.dkmkknub.villtech.room.RoomViewModel
import com.dkmkknub.villtech.utils.LoginChecker
import com.dkmkknub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.ext.android.getViewModel

lateinit var roomViewModel: RoomViewModel
lateinit var rootViewModel: RootViewModel
lateinit var loginChecker: LoginChecker
lateinit var navController: NavHostController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberAnimatedNavController()
            loginChecker = get()
            roomViewModel = getViewModel()
            rootViewModel = getViewModel()

            VillTechTheme {
                VillTechApp(navController)
            }
        }
    }


    override fun onRestart() {
        super.onRestart()
        if(loginChecker.isLoginWithAdmin){
            navController.popBackStack()
            navController.navigate(route = NavigationRoute.OnboardScreen.name)
            loginChecker.isLoginWithAdmin = false
        }
    }
}