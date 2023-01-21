package com.dkmkknub.villtech.room

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dkmkknub.villtech.repository.room.IRoomRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class RoomViewModel(private val repo: IRoomRepository) : ViewModel() {
    val listOfBookmarkedContent = mutableStateOf<List<BookmarkedEntity>?>(null)
    suspend fun getListOfBookmarkedContent(): Flow<List<BookmarkedEntity>> = repo.getBookmarkedList()
    suspend fun removeBookmarkContent(contentId:String){
        var isRemoved by mutableStateOf(false)

        if(!isRemoved){
            getListOfBookmarkedContent().collect{
                it.forEach{ item ->
                    if(item.contentId.equals(contentId)) {
                        CoroutineScope(Dispatchers.IO).launch {
                            repo.removeBookmarkedContent(item)
                        }
                    }
                }

                isRemoved = true
            }
        }

    }
    suspend fun addBookmarkedContent(content: BookmarkedEntity) {
        var isContains by mutableStateOf(false)
        var isChecked by mutableStateOf(false)

        getListOfBookmarkedContent().collect {
            viewModelScope.launch {
                if (it.isEmpty())
                    repo.addBookmarkedContent(content)
                else
                    it.forEachIndexed { index, item ->
                        if (item.contentId.contains(content.contentId)) isContains = true
                        if (index == it.size - 1) isChecked = true

                        if (isChecked)
                            if (!isContains) {
                                repo.addBookmarkedContent(content)
                                isContains = false
                                isChecked = false
                            }
                    }
            }
        }
    }
}