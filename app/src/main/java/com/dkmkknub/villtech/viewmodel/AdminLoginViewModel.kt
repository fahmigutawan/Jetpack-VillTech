package com.dkmkknub.villtech.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.dkmkknub.villtech.repository.firebase.FirebaseRepository

class AdminLoginViewModel(private val firebaseRepository: FirebaseRepository):ViewModel() {
    var emailState = mutableStateOf("")
    var passwordState = mutableStateOf("")
    fun loginWithEmailPassword(
        email: String,
        password: String,
        onSuccess: ((FirebaseUser) -> Unit)? = null,
        onFailed: ((Exception) -> Unit)? = null
    ) {
        firebaseRepository
            .loginWithEmailPassword(email, password)
            .addOnSuccessListener {
                if (onSuccess != null) {
                    if(getUserData()!=null){
                        onSuccess(getUserData()!!)
                    }
                }
            }
            .addOnFailureListener {
                if (onFailed != null) {
                    onFailed(it)
                }
            }
    }

    fun getUserData(): FirebaseUser?{
        return firebaseRepository.getUserData()
    }

    fun logout(){
        firebaseRepository.logout()
    }
}