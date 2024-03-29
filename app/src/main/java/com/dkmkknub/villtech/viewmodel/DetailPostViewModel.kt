package com.dkmkknub.villtech.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkmkknub.villtech.repository.firebase.FirebaseRepository
import com.dkmkknub.villtech.roomViewModel
import kotlinx.coroutines.launch

class DetailPostViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel() {
    var isFavorite = mutableStateOf(false)
    var isLoaded by mutableStateOf(false)
    var isBookmarkRemoved = mutableStateOf(false)
    var isBookmarkAdded by mutableStateOf(false)
    fun checkIsFavorite(contentId: String) {
        if (!isLoaded) {
            viewModelScope.launch {
                roomViewModel.getListOfBookmarkedContent().collect {
                    it.forEach { result ->
                        if (result.contentId.contains(contentId)) {
                            isFavorite.value = true
                        }
                    }
                    isLoaded = true
                }
            }
        }
    }
    fun checkMediaType(contentId: String, onRetrieved: (String) -> Unit) {
        firebaseRepository
            .database()
            .reference
            .child("content")
            .child("content_list")
            .child(contentId)
            .child("type")
            .get()
            .addOnSuccessListener {
                onRetrieved(it.value.toString())
            }
    }
}