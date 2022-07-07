package com.ub.villtech.repository.room

import androidx.lifecycle.LiveData
import com.ub.villtech.model.OnBookmarkedContent
import com.ub.villtech.room.BookmarkedEntity
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val iRoomRepository: IRoomRepository) {
    val getBookmarkedList:Flow<List<OnBookmarkedContent>> = iRoomRepository.getBookmarkedList()
    suspend fun addBookmarkedContent(content:BookmarkedEntity){
        iRoomRepository.addBookmarkedContent(content)
    }
}