package com.dkmkknub.villtech.repository.firebase

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class FirebaseRepository(
    private val authInstance: FirebaseAuth,
    private val fbDbInstance: FirebaseDatabase,
    private val fbStorageInstance: FirebaseStorage
) {
    /**Get Instance*/
    fun auth() = authInstance

    fun storage() = fbStorageInstance

    fun database() = fbDbInstance

    /**Firebase Authentication Section*/
    fun loginWithEmailPassword(email: String, password: String): Task<AuthResult> =
        authInstance.signInWithEmailAndPassword(email, password)

    fun getUserData(): FirebaseUser? = authInstance.currentUser

    fun logout() {
        authInstance.signOut()
    }

    /**Firebase Database Section*/
    fun getAdminInfo(uid: String): Task<DataSnapshot> =
        fbDbInstance.reference.child("admin").child(uid).get()

    fun getContentList(): Task<DataSnapshot> = fbDbInstance.reference.child("content").get()

    fun getContentCount() = fbDbInstance.reference.child("content").child("count").get()

    fun updateContentCount(count: Int) =
        fbDbInstance.reference.child("content").child("count").setValue(count)

    fun getAdminList(): Task<DataSnapshot> = fbDbInstance.reference.child("admin").get()

    /**Firebase Storage Section*/
    fun getMediaRef(contentId: Int) =
        fbStorageInstance.reference.child("content").child("$contentId")
}