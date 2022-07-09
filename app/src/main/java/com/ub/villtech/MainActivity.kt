package com.ub.villtech

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ub.villtech.ui.theme.VillTechTheme
import com.ub.villtech.room.RoomViewModel
import com.ub.villtech.room.RoomViewModelFactory
import com.ub.villtech.viewmodel.AdminLoginViewModel
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

lateinit var roomViewModel: RoomViewModel
lateinit var rootViewModel: RootViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            roomViewModel =
                viewModel(factory = RoomViewModelFactory(LocalContext.current.applicationContext as Application))
            rootViewModel = getViewModel()

            VillTechTheme {
                VillTechApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val loginViewModel = getViewModel<AdminLoginViewModel>()
        loginViewModel.logout()
    }
}