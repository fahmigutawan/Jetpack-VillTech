package com.ub.villtech.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ub.villtech.navigation.NavigationRoute

class BottomNavigationViewModel : ViewModel() {
    var selectState by mutableStateOf(NavigationRoute.HomeScreen)
}