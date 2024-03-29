package com.dkmkknub.villtech.repository.room

import com.dkmkknub.villtech.room.BookmarkedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class RoomRepository(private val iRoomRepository: IRoomRepository) {
    suspend fun getBookmarkedList():Flow<List<BookmarkedEntity>> = flow{
        emit(iRoomRepository.getBookmarkedList().first())
    }
    suspend fun addBookmarkedContent(content: BookmarkedEntity){
        iRoomRepository.addBookmarkedContent(content)
    }
    suspend fun removeBookmarkedContent(content: BookmarkedEntity) {
        iRoomRepository.removeBookmarkedContent(content)
    }
}