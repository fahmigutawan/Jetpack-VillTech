package com.ub.villtech.model

data class ContentItem(
    val title: String,
    val author: String,
    val category:String,
    val media_url: String,
    val thumbnail_url:String,
    val post_date: String,
    val description: String,
    val commentCount: String,
    val commentList: List<ContentComment>
)

data class ContentComment(
    val comment:String
)