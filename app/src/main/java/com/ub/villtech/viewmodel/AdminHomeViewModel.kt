package com.ub.villtech.viewmodel

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ub.villtech.model.AdminInfo
import com.ub.villtech.repository.firebase.FirebaseRepository
import com.ub.villtech.rootViewModel
import com.ub.villtech.screen.AdminAdminHomeScreen
import com.ub.villtech.screen.AdminHomeScreenTopBarSelection
import com.ub.villtech.ui.theme.GreenMint
import com.ub.villtech.ui.theme.Light
import kotlinx.coroutines.launch

class AdminHomeViewModel(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    var adminColor by mutableStateOf(GreenMint)
    var postingColor by mutableStateOf(Color.Gray)
    var selectState by mutableStateOf(AdminHomeScreenTopBarSelection.Admin)
    var content by mutableStateOf<@Composable () -> Unit>({ AdminAdminHomeScreen() })

    var listOfAdmin = ArrayList<AdminInfo>()
    var isAdminLoaded by mutableStateOf(false)
    fun getAdminList() {
        isAdminLoaded = false
        viewModelScope.launch {
            firebaseRepository
                .getAdminList()
                .addOnSuccessListener {
                    it.children.forEach {
                        listOfAdmin.add(
                            AdminInfo(
                                name = it.child("name").value.toString(),
                                image_url = it.child("image_url").value.toString()
                            )
                        )
                    }

                    isAdminLoaded = true
                }
                .addOnFailureListener {
                    isAdminLoaded = false
                    rootViewModel.showSnackbar("Terjadi kesalahan, coba lagi nanti")
                }
        }
    }
}