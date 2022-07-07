package com.ub.villtech.room

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ub.villtech.VillTechDatabase
import com.ub.villtech.model.OnBookmarkedContent
import com.ub.villtech.repository.room.RoomRepository
import kotlinx.coroutines.launch

class RoomViewModel(application: Application) : ViewModel() {
    private val repo: RoomRepository

    init {
        val iRoomRepository = VillTechDatabase.getInstance(application).iRoomRepository()
        repo = RoomRepository(iRoomRepository)
    }

    val listOfBookmarkedContent = mutableStateOf<List<OnBookmarkedContent>?>(null)
    fun getListOfBookmarkedContent() {
        viewModelScope.launch {
            repo.getBookmarkedList.collect(collector = {
                if(it.isNotEmpty()){
                    listOfBookmarkedContent.value = it
                }
                /**DO SOMETHING*/
            })
        }
    }

    fun addBookmarkedContent(content:BookmarkedEntity){
        viewModelScope.launch {
            repo.addBookmarkedContent(content)
        }
    }
}