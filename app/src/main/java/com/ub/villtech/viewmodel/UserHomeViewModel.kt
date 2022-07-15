package com.ub.villtech.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ub.villtech.model.ContentComment
import com.ub.villtech.model.ContentItem
import com.ub.villtech.repository.firebase.FirebaseRepository
import com.ub.villtech.rootViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel

class UserHomeViewModel(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {
    val contentList = mutableStateListOf<ContentItem>()
    var contentCount by mutableStateOf(-1)
    var lastLoadedId by mutableStateOf(-1)
    var isLoaded by mutableStateOf(false)
    fun getContentList(count: Int, onLoading: () -> Unit, onSuccess: () -> Unit) {
        onLoading()
        isLoaded = false
        viewModelScope.launch {
            firebaseRepository
                .getContentList()
                .addOnSuccessListener {
                    contentCount = (it.child("count").value.toString()).toInt()

                    if (lastLoadedId == (-1)) {
                        lastLoadedId = (it.child("count").value.toString()).toInt()
                    }

                    if (contentCount != contentList.size) {
                        for (i in 0 until count)
                            if (lastLoadedId != 0) {
                                val res =
                                    it
                                        .child("content_list")
                                        .child("${lastLoadedId}")

                                val commentList = ArrayList<ContentComment>()
                                it
                                    .child("comment_list")
                                    .children
                                    .forEach { commentList.add(ContentComment(comment = it.value.toString())) }

                                contentList.add(
                                    ContentItem(
                                        content_id = res.child("content_id").value.toString(),
                                        title = res.child("title").value.toString(),
                                        author = res.child("author").value.toString(),
                                        category = res.child("category").value.toString(),
                                        media_url = res.child("media_url").value.toString(),
                                        thumbnail_url = res.child("thumbnail").value.toString(),
                                        post_date = res.child("post_date").value.toString(),
                                        description = res.child("description").value.toString(),
                                        commentCount = res.child("commentCount").value.toString(),
                                        commentList = commentList
                                    )
                                )

                                lastLoadedId -= 1
                            } else {
                                rootViewModel.showSnackbar("Item sudah habis")
                                lastLoadedId -= 1
                                break
                            }
                    }
                    onSuccess()
                }
                .addOnFailureListener {
                    rootViewModel.showSnackbar("Gagal memuat, coba lagi nanti!")
                    isLoading = false
                }
        }
    }

    var isLoading by mutableStateOf(false)
}