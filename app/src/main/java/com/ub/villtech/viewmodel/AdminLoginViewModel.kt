package com.ub.villtech.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ub.villtech.repository.Repository

class AdminLoginViewModel(repo:Repository):ViewModel() {
    var emailState = mutableStateOf("")
    var passwordState = mutableStateOf("")
}