package com.ub.villtech.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ub.villtech.model.OnBookmarkedContent
import com.ub.villtech.room.BookmarkedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IRoomRepository {
    @Query("SELECT * from bookmarked_content")
    fun getBookmarkedList():Flow<List<OnBookmarkedContent>>

    @Insert
    suspend fun addBookmarkedContent(content:BookmarkedEntity)
}