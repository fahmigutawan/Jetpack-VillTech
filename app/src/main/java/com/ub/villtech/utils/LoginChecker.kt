package com.ub.villtech.utils

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import com.ub.villtech.model.AdminInfo
import com.ub.villtech.repository.firebase.FirebaseRepository

class LoginChecker(
    private val firebaseRepository: FirebaseRepository,
    private val auth: FirebaseAuth
) {
    var isLoginWithAdmin = false
    var adminInfo = AdminInfo("...", "null", "")
    fun loadAdminInfo() =
        firebaseRepository.getAdminInfo(auth.currentUser!!.uid).addOnSuccessListener {
            adminInfo = AdminInfo(
                name = it.child("name").value.toString(),
                image_url = it.child("image_url").value.toString(),
                uid = it.child("uid").value.toString()
            )
        }
}