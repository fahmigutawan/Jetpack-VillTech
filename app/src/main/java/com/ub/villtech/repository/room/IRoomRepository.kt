package com.ub.villtech.repository.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ub.villtech.model.OnBookmarkedContent
import com.ub.villtech.room.BookmarkedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IRoomRepository {
    @Query("SELECT * from bookmarked_content")
    fun getBookmarkedList():Flow<List<BookmarkedEntity>>

    @Insert
    suspend fun addBookmarkedContent(content:BookmarkedEntity)

    @Delete
    suspend fun removeBookmarkedContent(content:BookmarkedEntity)
}