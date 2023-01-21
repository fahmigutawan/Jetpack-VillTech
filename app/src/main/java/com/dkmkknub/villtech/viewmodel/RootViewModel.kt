package com.dkmkknub.villtech.viewmodel

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkmkknub.villtech.navigation.NavigationRoute
import com.dkmkknub.villtech.scaffoldState
import kotlinx.coroutines.launch

class RootViewModel() : ViewModel() {
    var showBottomBar by mutableStateOf(false)
    val bottomBarState = mutableStateOf(NavigationRoute.SplashScreen.name)
    var contentHeight by mutableStateOf(0)
    fun showSnackbar(message: String) {
        viewModelScope.launch {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            scaffoldState.snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }
}
