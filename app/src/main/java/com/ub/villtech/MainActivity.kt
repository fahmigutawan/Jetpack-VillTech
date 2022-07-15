package com.ub.villtech

import android.app.Application
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ub.villtech.navigation.NavigationRoute
import com.ub.villtech.ui.theme.VillTechTheme
import com.ub.villtech.room.RoomViewModel
import com.ub.villtech.utils.LoginChecker
import com.ub.villtech.viewmodel.AdminLoginViewModel
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.ext.android.getViewModel

lateinit var roomViewModel: RoomViewModel
lateinit var rootViewModel: RootViewModel
lateinit var loginChecker:LoginChecker
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