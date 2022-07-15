package com.ub.villtech.room

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ub.villtech.VillTechDatabase
import com.ub.villtech.model.OnBookmarkedContent
import com.ub.villtech.repository.room.IRoomRepository
import com.ub.villtech.repository.room.RoomRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.viewModel

class RoomViewModel(private val repo:IRoomRepository) : ViewModel() {
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