package com.ub.villtech.viewmodel

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ub.villtech.scaffoldState
import kotlinx.coroutines.launch

class RootViewModel:ViewModel() {
    var isBottomNavigationEnabled by mutableStateOf(false)
    fun showSnackbar(message:String){
        viewModelScope.launch {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            scaffoldState.snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
        }
    }
}
