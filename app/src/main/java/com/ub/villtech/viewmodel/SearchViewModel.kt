package com.ub.villtech.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ub.villtech.repository.firebase.FirebaseRepository
import com.ub.villtech.screen.user.SearchScreenTopMenuSelection

class SearchViewModel(firebaseRepository: FirebaseRepository) : ViewModel() {
    var topMenuSelected by mutableStateOf(SearchScreenTopMenuSelection.All)
    var topMenuIndexSelected by mutableStateOf(0)
    var searchState = mutableStateOf("")
}