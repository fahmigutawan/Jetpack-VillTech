package com.ub.villtech.repository.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class FirebaseRepository(
    private val authInstance: FirebaseAuth,
    private val fbDbInstance: FirebaseDatabase,
    private val fbStorageInstance: FirebaseStorage
) {
    /**Firebase Authentication Section*/
    fun loginWithEmailPassword(email: String, password: String): Task<AuthResult> {
        return authInstance.signInWithEmailAndPassword(email, password)
    }

    fun getUserData():FirebaseUser?{
        return authInstance.currentUser
    }

    fun logout(){
        authInstance.signOut()
    }

    /**Firebase Database Section*/
    fun getContentList():Task<DataSnapshot>{
        return fbDbInstance.reference.child("content").get()
    }

    fun getAdminList():Task<DataSnapshot>{
        return fbDbInstance.reference.child("admin").get()
    }
}