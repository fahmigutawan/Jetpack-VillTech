package com.dkmkknub.villtech.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkmkknub.villtech.model.AdminInfo
import com.dkmkknub.villtech.repository.firebase.FirebaseRepository
import com.dkmkknub.villtech.rootViewModel
import com.dkmkknub.villtech.screen.AdminAdminHomeScreen
import com.dkmkknub.villtech.screen.AdminHomeScreenTopBarSelection
import com.dkmkknub.villtech.ui.theme.Dark
import com.dkmkknub.villtech.ui.theme.GreenMint
import com.dkmkknub.villtech.utils.LoginChecker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AdminHomeViewModel(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    var listOfAdmin = ArrayList<AdminInfo>()
    var content by mutableStateOf<@Composable () -> Unit>({ AdminAdminHomeScreen() })
    var isAdminLoaded by mutableStateOf(false)

    var selectState by mutableStateOf(AdminHomeScreenTopBarSelection.Admin)
    var titleValueState = mutableStateOf("")
    var descriptionValueState = mutableStateOf("")

    var adminColor by mutableStateOf(GreenMint)
    var postingColor by mutableStateOf(Color.Gray)

    var isCategoryInfografisSelected by mutableStateOf(false)
    var isCategoryVideoSelected by mutableStateOf(false)
    var isUploadButtonEnabled by mutableStateOf(true)

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
                                image_url = it.child("image_url").value.toString(),
                                uid = it.child("uid").value.toString()
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

    //Media picker
    var isVideoIsFit: (Context, Uri) -> Boolean = { myContext, uri ->
        myContext.contentResolver.openAssetFileDescriptor(uri, "r")!!.length <= 262144000
    }
    var videoUri by mutableStateOf<Uri?>(null)
    var photoUri = mutableStateListOf<Uri>()
    var uploadPhotoEnabled by mutableStateOf(true)
    var uploadVideoEnabled by mutableStateOf(true)
    var uploadPhotoColor by mutableStateOf(Dark)
    var uploadVideoColor by mutableStateOf(Dark)

    //Upload
    var uploadProgress by mutableStateOf(1f)
    var isUploading by mutableStateOf(false)
    var mp4Thumbnail by mutableStateOf<Uri?>(null)
    var category by mutableStateOf("")

    fun updateCount(count: Int) = firebaseRepository.updateContentCount(count)

    fun uploadMp4(contentId: Int, onSuccess: () -> Unit, onFailed: () -> Unit) {
        var successCount by mutableStateOf(0)

        if (mp4Thumbnail == null) {
            rootViewModel.showSnackbar("Harap pilih thumbnail")
            return
        }

        /**Success Checker*/
        val checkSuccess: () -> Unit = {
            if (successCount == 2) onSuccess()
        }

        /**Upload video*/
        firebaseRepository
            .storage()
            .reference
            .child("content")
            .child("$contentId")
            .child("video")
            .putFile(videoUri!!)
            .addOnProgressListener {
                uploadProgress = 0f
                isUploading = true
                isUploadButtonEnabled = false
                uploadProgress =
                    (it.bytesTransferred.toDouble()
                            / it.totalByteCount.toDouble()).toFloat()
            }
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener {
                isUploading = false
                isUploadButtonEnabled = true
                uploadProgress = 1f
                onFailed()
            }

        /**Upload thumbnail*/
        firebaseRepository
            .storage()
            .reference
            .child("content")
            .child("$contentId")
            .child("thumbnail")
            .putFile(mp4Thumbnail!!)
            .addOnProgressListener {
                uploadProgress = 0f
                isUploading = true
                isUploadButtonEnabled = false
                uploadProgress =
                    (it.bytesTransferred.toDouble()
                            / it.totalByteCount.toDouble()).toFloat()
            }
            .addOnSuccessListener {
                successCount++
            }
            .addOnFailureListener {
                isUploading = false
                isUploadButtonEnabled = true
                uploadProgress = 1f
                onFailed()
            }
    }

    fun uploadJpeg(contentId: Int, onSuccess: () -> Unit, onFailed: () -> Unit) {
        var successCount by mutableStateOf(0)

        /**Success Checker*/
        val checkSuccess: () -> Unit = {
            if (successCount == photoUri.size) onSuccess()

        }

        /**Upload all images with forEach*/
        photoUri.forEachIndexed { index, uri ->
            firebaseRepository
                .storage()
                .reference
                .child("content")
                .child("$contentId")
                .child("foto$index")
                .putFile(uri)
                .addOnProgressListener {
                    uploadProgress = 0f
                    isUploading = true
                    isUploadButtonEnabled = false
                    uploadProgress =
                        (it.bytesTransferred.toDouble()
                                / it.totalByteCount.toDouble()).toFloat()
                }
                .addOnSuccessListener {
                    successCount++
                    checkSuccess()
                }
                .addOnFailureListener {
                    onFailed()
                }
        }
    }

    fun getContentCount() = firebaseRepository.getContentCount()

    fun uploadPostWithImage(
        contentId: Int,
        loginChecker: LoginChecker,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        val post_date = SimpleDateFormat("dd-MM-yyyy").format(Date())
        var successCount by mutableStateOf(0)

        /**Success Checker*/
        val checkSuccess: () -> Unit = {
            if (successCount == (9 + photoUri.size)) {
                onSuccess()
                isUploading = false
                isUploadButtonEnabled = true
                uploadProgress = 1f
            }
        }

        /**Upload Normal Values*/
        val ref =
            firebaseRepository
                .database()
                .reference
                .child("content")
                .child("content_list")
                .child("$contentId")

        ref.child("content_id").setValue(contentId.toString())
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
        ref.child("title").setValue(titleValueState.value)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("author").setValue(loginChecker.adminInfo.name)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("type").setValue("image")
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("category").setValue(category)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("post_date").setValue(post_date)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("description").setValue(descriptionValueState.value)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("commentCount").setValue(0)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }

        /**Upload media link from storage*/
        //Upload iamges
        repeat(photoUri.size){ index ->
            firebaseRepository
                .storage()
                .reference
                .child("content")
                .child("$contentId")
                .child("foto$index")
                .downloadUrl
                .addOnSuccessListener { uri ->
                    ref
                        .child("media_url")
                        .child("foto$index")
                        .setValue(uri.toString())
                        .addOnSuccessListener {
                            successCount++
                            checkSuccess()
                        }
                        .addOnFailureListener { onFailed() }
                }
        }

        //Upload thumbnail
        firebaseRepository
            .storage()
            .reference
            .child("content")
            .child("$contentId").child("foto0").downloadUrl.addOnSuccessListener { uri ->
                ref
                    .child("thumbnail")
                    .setValue(uri.toString())
                    .addOnSuccessListener {
                        successCount++
                        checkSuccess()
                    }
                    .addOnFailureListener { onFailed() }
            }
    }

    fun uploadPostWithVideo(
        contentId: Int,
        loginChecker: LoginChecker,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    ) {
        val post_date = SimpleDateFormat("dd-MM-yyyy").format(Date())
        var successCount by mutableStateOf(0)

        /**Success Checker*/
        val checkSuccess: () -> Unit = {
            if (successCount == 10){
                onSuccess()
                isUploading = false
                isUploadButtonEnabled = true
                uploadProgress = 1f
            }
        }

        /**Upload Normal Values*/
        val ref =
            firebaseRepository
                .database()
                .reference
                .child("content")
                .child("content_list")
                .child("$contentId")
        ref.child("content_id").setValue(contentId.toString())
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
        ref.child("title").setValue(titleValueState.value)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("author").setValue(loginChecker.adminInfo.name)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("type").setValue("video")
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("category").setValue(category)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("post_date").setValue(post_date)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("description").setValue(descriptionValueState.value)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }
        ref.child("commentCount").setValue(0)
            .addOnSuccessListener {
                successCount++
                checkSuccess()
            }
            .addOnFailureListener { onFailed() }

        /**Upload media links from storage*/
        /**Upload Video link*/
        firebaseRepository
            .storage()
            .reference
            .child("content")
            .child("$contentId")
            .child("video")
            .downloadUrl
            .addOnSuccessListener { uri ->
                ref.child("media_url").setValue(uri.toString())
                    .addOnSuccessListener {
                        successCount++
                        checkSuccess()
                    }.addOnFailureListener { onFailed() }
            }
        /**Upload thumbnail*/
        firebaseRepository
            .storage()
            .reference
            .child("content")
            .child("$contentId")
            .child("thumbnail")
            .downloadUrl
            .addOnSuccessListener { uri ->
                ref.child("thumbnail").setValue(uri.toString())
                    .addOnSuccessListener {
                        successCount++
                        checkSuccess()
                    }.addOnFailureListener { onFailed() }
            }
    }

    fun resetPostData() {
        descriptionValueState.value = ""
        titleValueState.value = ""
        isCategoryInfografisSelected = false
        isCategoryVideoSelected = false
        uploadPhotoColor = Dark
        uploadVideoColor = Dark
        videoUri = null
        photoUri.clear()
        uploadPhotoEnabled = true
        uploadVideoEnabled = true
        isUploadButtonEnabled = true
        isUploading = false
        mp4Thumbnail = null
        category = ""
    }

    fun isDataNotFulfilled() =
        (titleValueState.value == ""
                || descriptionValueState.value == ""
                || (videoUri == null && photoUri.isEmpty())
                || (!isCategoryVideoSelected && !isCategoryInfografisSelected)
                )
}
