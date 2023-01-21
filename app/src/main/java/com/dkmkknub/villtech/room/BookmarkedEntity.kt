package com.dkmkknub.villtech.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_content")
data class BookmarkedEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int? = null,

    @ColumnInfo(name = "content_id")
    val contentId: String,
)