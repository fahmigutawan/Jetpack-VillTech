package com.ub.villtech.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.ub.villtech.model.ContentImageResponse
import com.ub.villtech.model.ContentVideoResponse
import com.ub.villtech.repository.firebase.FirebaseRepository
import kotlin.collections.ArrayList

class SearchViewModel(private val firebaseRepository: FirebaseRepository) : ViewModel() {
    var topMenuIndexSelected by mutableStateOf(0)
    var searchState = mutableStateOf("")

    var listOfResultWithImage by mutableStateOf<ArrayList<ContentImageResponse>?>(null)
    var listOfResultWithImageOffline by mutableStateOf<ArrayList<ContentImageResponse>?>(null)

    var listOfResultWithVideo by mutableStateOf<ArrayList<ContentVideoResponse>?>(null)
    var listOfResultWithVideoOffline by mutableStateOf<ArrayList<ContentVideoResponse>?>(null)

    fun searchOnFirstLetterAll() {
        val query = searchState.value
        var isImageLoaded by mutableStateOf(false)
        var isVideoLoaded by mutableStateOf(false)
        val tmpImg = ArrayList<ContentImageResponse>()
        val tmpVid = ArrayList<ContentVideoResponse>()

        val mapToListOfContentImage: (DataSnapshot) -> Unit = {
            val listOfUrl = ArrayList<String>()

            //Mapping image url first
            it.child("media_url")
                .children.forEach { image ->
                    listOfUrl.add(image.value.toString())
                }

            //Mapping into listOfResult
            tmpImg.add(
                ContentImageResponse(
                    content_id = it.child("content_id").value.toString(),
                    title = it.child("title").value.toString(),
                    author = it.child("author").value.toString(),
                    category = it.child("category").value.toString(),
                    media_url = listOfUrl,
                    thumbnail_url = it.child("thumbnail").value.toString(),
                    post_date = it.child("post_date").value.toString(),
                    description = it.child("description").value.toString(),
                    commentCount = it.child("commentCount").value.toString()
                )
            )

            listOfResultWithImage = tmpImg
        }
        val mapToListOfContentVideo: (DataSnapshot) -> Unit = {
            //Mapping into listOfResult
            tmpVid.add(
                ContentVideoResponse(
                    content_id = it.child("content_id").value.toString(),
                    title = it.child("title").value.toString(),
                    author = it.child("author").value.toString(),
                    category = it.child("category").value.toString(),
                    media_url = it.child("media_url").value.toString(),
                    thumbnail_url = it.child("thumbnail").value.toString(),
                    post_date = it.child("post_date").value.toString(),
                    description = it.child("description").value.toString(),
                    commentCount = it.child("commentCount").value.toString()
                )
            )

            listOfResultWithVideo = tmpVid
        }

        firebaseRepository
            .database()
            .reference
            .child("content")
            .child("content_list")
            .get()
            .addOnSuccessListener { result ->
                result.children
                    .forEach {
                        if (it.child("title").value.toString()
                                .replace(".", "")
                                .replace(",", "")
                                .replace("!", "")
                                .replace("?", "").contains(query, true)
                        ) {
                            when (it.child("type").value.toString()) {
                                "image" -> mapToListOfContentImage(it)
                                "video" -> mapToListOfContentVideo(it)
                            }
                        }
                    }
            }
    }

    fun searchSecondLetterAndMoreAll() {
        if (!listOfResultWithImageOffline.isNullOrEmpty()) listOfResultWithImageOffline!!.clear()
        if (!listOfResultWithVideoOffline.isNullOrEmpty()) listOfResultWithVideoOffline!!.clear()


        val query = searchState.value

        //SearchImageContent
        if (!listOfResultWithImage.isNullOrEmpty()) {
            val tmp = ArrayList<ContentImageResponse>()

            listOfResultWithImage!!.forEach {
                if (it.title.contains(query, true)) tmp.add(it)
            }

            listOfResultWithImageOffline = tmp
        }

        //SearchVideoContent
        if (!listOfResultWithVideo.isNullOrEmpty()) {
            val tmp = ArrayList<ContentVideoResponse>()

            listOfResultWithVideo!!.forEach {
                if (it.title.contains(query, true)) tmp.add(it)
            }

            listOfResultWithVideoOffline = tmp
        }
    }

    fun searchOnFirstLetterInfografis() {
        val query = searchState.value
        val tmpImg = ArrayList<ContentImageResponse>()
        val tmpVid = ArrayList<ContentVideoResponse>()

        val mapToListOfContentImage: (DataSnapshot) -> Unit = {
            val listOfUrl = ArrayList<String>()

            //Mapping image url first
            it.child("media_url")
                .children.forEach { image ->
                    listOfUrl.add(image.value.toString())
                }

            //Mapping into listOfResult
            tmpImg.add(
                ContentImageResponse(
                    content_id = it.child("content_id").value.toString(),
                    title = it.child("title").value.toString(),
                    author = it.child("author").value.toString(),
                    category = it.child("category").value.toString(),
                    media_url = listOfUrl,
                    thumbnail_url = it.child("thumbnail").value.toString(),
                    post_date = it.child("post_date").value.toString(),
                    description = it.child("description").value.toString(),
                    commentCount = it.child("commentCount").value.toString()
                )
            )

            listOfResultWithImage = tmpImg
        }
        val mapToListOfContentVideo: (DataSnapshot) -> Unit = {

            //Mapping into listOfResult
            tmpVid.add(
                ContentVideoResponse(
                    content_id = it.child("content_id").value.toString(),
                    title = it.child("title").value.toString(),
                    author = it.child("author").value.toString(),
                    category = it.child("category").value.toString(),
                    media_url = it.child("media_url").value.toString(),
                    thumbnail_url = it.child("thumbnail").value.toString(),
                    post_date = it.child("post_date").value.toString(),
                    description = it.child("description").value.toString(),
                    commentCount = it.child("commentCount").value.toString()
                )
            )

            listOfResultWithVideo = tmpVid
        }

        firebaseRepository
            .database()
            .reference
            .child("content")
            .child("content_list")
            .get()
            .addOnSuccessListener { result ->
                result.children
                    .forEach {
                        if (it.child("category").value.toString().contains("infografis", true)) {
                            if (it.child("title").value.toString()
                                        .contains(query, true)) {
                                when (it.child("type").value.toString()) {
                                    "image" -> mapToListOfContentImage(it)
                                    "video" -> mapToListOfContentVideo(it)
                                }
                            }
                        }
                    }
            }
    }

    fun searchSecondLetterAndMoreInfografis() {
        val query = searchState.value

        //SearchImageContent
        if (!listOfResultWithImage.isNullOrEmpty()) {
            val tmp = ArrayList<ContentImageResponse>()

            listOfResultWithImage!!.forEach {
                if (it.category.contains("infografis", true)) {
                    if (it.title.contains(query, true)) {
                        tmp.add(it)
                    }
                }
            }

            listOfResultWithImageOffline = tmp
        }

        //SearchVideoContent
        if (!listOfResultWithVideo.isNullOrEmpty()) {
            val tmp = ArrayList<ContentVideoResponse>()

            listOfResultWithVideo!!.forEach {
                if (it.category.contains("infografis", true)) {
                    if (it.title.contains(query, true)) {
                        tmp.add(it)
                    }
                }
            }

            listOfResultWithVideoOffline = tmp
        }
    }

    fun searchOnFirstLetterVideo() {
        val query = searchState.value
        val tmpImg = ArrayList<ContentImageResponse>()
        val tmpVid = ArrayList<ContentVideoResponse>()

        val mapToListOfContentImage: (DataSnapshot) -> Unit = {
            val listOfUrl = ArrayList<String>()

            //Mapping image url first
            it.child("media_url")
                .children.forEach { image ->
                    listOfUrl.add(image.value.toString())
                }

            //Mapping into listOfResult
            tmpImg.add(
                ContentImageResponse(
                    content_id = it.child("content_id").value.toString(),
                    title = it.child("title").value.toString(),
                    author = it.child("author").value.toString(),
                    category = it.child("category").value.toString(),
                    media_url = listOfUrl,
                    thumbnail_url = it.child("thumbnail").value.toString(),
                    post_date = it.child("post_date").value.toString(),
                    description = it.child("description").value.toString(),
                    commentCount = it.child("commentCount").value.toString()
                )
            )

            listOfResultWithImage = tmpImg
        }
        val mapToListOfContentVideo: (DataSnapshot) -> Unit = {
            //Mapping into listOfResult
            tmpVid.add(
                ContentVideoResponse(
                    content_id = it.child("content_id").value.toString(),
                    title = it.child("title").value.toString(),
                    author = it.child("author").value.toString(),
                    category = it.child("category").value.toString(),
                    media_url = it.child("media_url").value.toString(),
                    thumbnail_url = it.child("thumbnail").value.toString(),
                    post_date = it.child("post_date").value.toString(),
                    description = it.child("description").value.toString(),
                    commentCount = it.child("commentCount").value.toString()
                )
            )

            listOfResultWithVideo = tmpVid
        }

        firebaseRepository
            .database()
            .reference
            .child("content")
            .child("content_list")
            .get()
            .addOnSuccessListener { result ->
                result.children
                    .forEach {
                        if (it.child("category").value.toString().contains("video", true)) {
                            if (it.child("title").value.toString()
                                    .contains(query, true)) {
                                when (it.child("type").value.toString()) {
                                    "image" -> mapToListOfContentImage(it)
                                    "video" -> mapToListOfContentVideo(it)
                                }
                            }
                        }
                    }
            }
    }

    fun searchSecondLetterAndMoreVideo() {
        val query = searchState.value

        //SearchImageContent
        if (!listOfResultWithImage.isNullOrEmpty()) {
            val tmp = ArrayList<ContentImageResponse>()

            listOfResultWithImage!!.forEach {
                if (it.category.contains("video", true)) {
                    if (it.title.contains(query, true)) {
                        tmp.add(it)
                    }
                }
            }

            listOfResultWithImageOffline = tmp
        }

        //SearchVideoContent
        if (!listOfResultWithVideo.isNullOrEmpty()) {
            val tmp = ArrayList<ContentVideoResponse>()

            listOfResultWithVideo!!.forEach {
                if (it.category.contains("video", true)) {
                    if (it.title.contains(query, true)) {
                        tmp.add(it)
                    }
                }
            }

            listOfResultWithVideoOffline = tmp
        }
    }
}