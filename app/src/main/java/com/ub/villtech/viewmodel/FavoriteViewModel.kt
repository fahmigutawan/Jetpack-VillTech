package com.ub.villtech.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.ub.villtech.model.ContentImageResponse
import com.ub.villtech.model.ContentVideoResponse
import com.ub.villtech.repository.firebase.FirebaseRepository
import com.ub.villtech.repository.room.IRoomRepository
import com.ub.villtech.room.BookmarkedEntity
import com.ub.villtech.screen.FavoriteScreenTopMenuSelection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val firebaseRepository: FirebaseRepository,
    private val repo: IRoomRepository
) : ViewModel() {
    //State
    var topMenuSelected by mutableStateOf(FavoriteScreenTopMenuSelection.All)
    var topMenuIndexSelected by mutableStateOf(0)
    var searchState = mutableStateOf("")

    //Success listener
    var allCategorySuccessLoadedCount by mutableStateOf(0)
    var infografisSuccessLoadedCount by mutableStateOf(0)
    var videoSuccessLoadedCount by mutableStateOf(0)
    var isAllContentLoaded by mutableStateOf(false)
    var isInfografisContentLoaded by mutableStateOf(false)
    var isVideoContentLoaded by mutableStateOf(false)

    //List of Content
    var listOfFavoriteContentAllWithImage = mutableStateListOf<ContentImageResponse>()
    var listOfFavoriteContentAllWithVideo = mutableStateListOf<ContentVideoResponse>()
    var listOfFavoriteContentInfografisWithImage = mutableStateListOf<ContentImageResponse>()
    var listOfFavoriteContentInfografisWithVideo = mutableStateListOf<ContentVideoResponse>()
    var listOfFavoriteContentVideoWithImage = mutableStateListOf<ContentImageResponse>()
    var listOfFavoriteContentVideoWithVideo = mutableStateListOf<ContentVideoResponse>()

    fun checkFavoriteCategory(contentId: String, onRetrieved: (DataSnapshot, String) -> Unit) {
        firebaseRepository
            .database()
            .reference
            .child("content")
            .child("content_list")
            .child(contentId)
            .get()
            .addOnSuccessListener {
                onRetrieved(it, it.child("category").value.toString())
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

    //Get content
    fun getContentInfografis(contentId: String, onSuccess: () -> Unit) {
        val getContentWithImage: () -> Unit = {
            checkFavoriteCategory(contentId) { snapshot, categoryResult ->
                //Load image url first
                val listOfUrl = mutableStateListOf<String>()
                val getContent: () -> Unit = {
                    if (categoryResult.contains("infografis", true)) {
                        listOfFavoriteContentInfografisWithImage.add(
                            ContentImageResponse(
                                content_id = contentId,
                                title = snapshot.child("title").value.toString(),
                                author = snapshot.child("author").value.toString(),
                                category = snapshot.child("category").value.toString(),
                                media_url = listOfUrl,
                                thumbnail_url = snapshot.child("thumbnail").value.toString(),
                                post_date = snapshot.child("post_date").value.toString(),
                                description = snapshot.child("description").value.toString(),
                                commentCount = snapshot.child("commentCount").value.toString()
                            )
                        )
                    }
                }

                val iterator = snapshot
                    .child("media_url")
                    .children.iterator()

                while (iterator.hasNext()) {
                    listOfUrl.add(iterator.next().value.toString())

                    if (!iterator.hasNext()) {
                        getContent()
                    }
                }

                onSuccess()
            }
        }
        val getContentWithVideo: () -> Unit = {
            checkFavoriteCategory(contentId) { snapshot, categoryResult ->
                if (categoryResult.contains("infografis", true)) {
                    listOfFavoriteContentInfografisWithVideo.add(
                        ContentVideoResponse(
                            content_id = contentId,
                            title = snapshot.child("title").value.toString(),
                            author = snapshot.child("author").value.toString(),
                            category = snapshot.child("category").value.toString(),
                            media_url = snapshot.child("media_url").value.toString(),
                            thumbnail_url = snapshot.child("thumbnail").value.toString(),
                            post_date = snapshot.child("post_date").value.toString(),
                            description = snapshot.child("description").value.toString(),
                            commentCount = snapshot.child("commentCount").value.toString()
                        )
                    )
                }

                onSuccess()
            }
        }
        checkMediaType(contentId) { type ->
            when (type) {
                "image" -> getContentWithImage()
                "video" -> getContentWithVideo()
            }
        }
    }

    fun getContentVideo(contentId: String, onSuccess: () -> Unit) {
        val getContentWithImage: () -> Unit = {
            checkFavoriteCategory(contentId) { snapshot, categoryResult ->
                //Load image url first
                val listOfUrl = mutableStateListOf<String>()
                val getContent: () -> Unit = {
                    if (categoryResult.contains("video", true)) {
                        listOfFavoriteContentVideoWithImage.add(
                            ContentImageResponse(
                                content_id = contentId,
                                title = snapshot.child("title").value.toString(),
                                author = snapshot.child("author").value.toString(),
                                category = snapshot.child("category").value.toString(),
                                media_url = listOfUrl,
                                thumbnail_url = snapshot.child("thumbnail").value.toString(),
                                post_date = snapshot.child("post_date").value.toString(),
                                description = snapshot.child("description").value.toString(),
                                commentCount = snapshot.child("commentCount").value.toString()
                            )
                        )
                    }
                }

                val iterator = snapshot
                    .child("media_url")
                    .children.iterator()

                while (iterator.hasNext()) {
                    listOfUrl.add(iterator.next().value.toString())

                    if (!iterator.hasNext()) getContent()
                }

                onSuccess()
            }
        }
        val getContentWithVideo: () -> Unit = {
            checkFavoriteCategory(contentId) { snapshot, categoryResult ->
                if (categoryResult.contains("video", true)) {
                    listOfFavoriteContentVideoWithVideo.add(
                        ContentVideoResponse(
                            content_id = contentId,
                            title = snapshot.child("title").value.toString(),
                            author = snapshot.child("author").value.toString(),
                            category = snapshot.child("category").value.toString(),
                            media_url = snapshot.child("media_url").value.toString(),
                            thumbnail_url = snapshot.child("thumbnail").value.toString(),
                            post_date = snapshot.child("post_date").value.toString(),
                            description = snapshot.child("description").value.toString(),
                            commentCount = snapshot.child("commentCount").value.toString()
                        )
                    )
                }

                onSuccess()
            }
        }
        checkMediaType(contentId) { type ->
            when (type) {
                "image" -> getContentWithImage()
                "video" -> getContentWithVideo()
            }
        }
    }

    fun getContentAll(contentId: String, onSuccess: () -> Unit) {
        val getContentWithImage: () -> Unit = {
            checkFavoriteCategory(contentId) { snapshot, categoryResult ->
                //Load image url first
                val listOfUrl = mutableStateListOf<String>()
                val getContent: () -> Unit = {
                    if (categoryResult.contains("infografis", true)) {
                        listOfFavoriteContentAllWithImage.add(
                            ContentImageResponse(
                                content_id = contentId,
                                title = snapshot.child("title").value.toString(),
                                author = snapshot.child("author").value.toString(),
                                category = snapshot.child("category").value.toString(),
                                media_url = listOfUrl,
                                thumbnail_url = snapshot.child("thumbnail").value.toString(),
                                post_date = snapshot.child("post_date").value.toString(),
                                description = snapshot.child("description").value.toString(),
                                commentCount = snapshot.child("commentCount").value.toString()
                            )
                        )
                    } else if (categoryResult.contains("video", true)) {
                        listOfFavoriteContentAllWithImage.add(
                            ContentImageResponse(
                                content_id = contentId,
                                title = snapshot.child("title").value.toString(),
                                author = snapshot.child("author").value.toString(),
                                category = snapshot.child("category").value.toString(),
                                media_url = listOfUrl,
                                thumbnail_url = snapshot.child("thumbnail").value.toString(),
                                post_date = snapshot.child("post_date").value.toString(),
                                description = snapshot.child("description").value.toString(),
                                commentCount = snapshot.child("commentCount").value.toString()
                            )
                        )
                    }
                }

                val iterator = snapshot
                    .child("media_url")
                    .children.iterator()

                while (iterator.hasNext()) {
                    listOfUrl.add(iterator.next().value.toString())

                    if (!iterator.hasNext()) getContent()
                }

                onSuccess()
            }
        }
        val getContentWithVideo: () -> Unit = {
            checkFavoriteCategory(contentId) { snapshot, categoryResult ->
                if (categoryResult.contains("infografis", true)) {
                    listOfFavoriteContentAllWithVideo.add(
                        ContentVideoResponse(
                            content_id = contentId,
                            title = snapshot.child("title").value.toString(),
                            author = snapshot.child("author").value.toString(),
                            category = snapshot.child("category").value.toString(),
                            media_url = snapshot.child("media_url").value.toString(),
                            thumbnail_url = snapshot.child("thumbnail").value.toString(),
                            post_date = snapshot.child("post_date").value.toString(),
                            description = snapshot.child("description").value.toString(),
                            commentCount = snapshot.child("commentCount").value.toString()
                        )
                    )
                } else if (categoryResult.contains("video", true)) {
                    listOfFavoriteContentAllWithVideo.add(
                        ContentVideoResponse(
                            content_id = contentId,
                            title = snapshot.child("title").value.toString(),
                            author = snapshot.child("author").value.toString(),
                            category = snapshot.child("category").value.toString(),
                            media_url = snapshot.child("media_url").value.toString(),
                            thumbnail_url = snapshot.child("thumbnail").value.toString(),
                            post_date = snapshot.child("post_date").value.toString(),
                            description = snapshot.child("description").value.toString(),
                            commentCount = snapshot.child("commentCount").value.toString()
                        )
                    )
                }

                onSuccess()
            }
        }

        checkMediaType(contentId) { type ->
            when (type) {
                "image" -> getContentWithImage()
                "video" -> getContentWithVideo()
            }
        }
    }

    fun resetState(){
        listOfFavoriteContentAllWithImage.clear()
        listOfFavoriteContentAllWithVideo.clear()
        listOfFavoriteContentInfografisWithImage.clear()
        listOfFavoriteContentInfografisWithVideo.clear()
        listOfFavoriteContentVideoWithImage.clear()
        listOfFavoriteContentVideoWithVideo.clear()

        isAllContentLoaded = false
        isInfografisContentLoaded = false
        isVideoContentLoaded = false

        allCategorySuccessLoadedCount = 0
        infografisSuccessLoadedCount = 0
        videoSuccessLoadedCount = 0
    }
}