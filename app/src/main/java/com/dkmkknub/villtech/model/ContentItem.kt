package com.dkmkknub.villtech.model

data class ContentItem(
    val content_id:String,
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

data class ContentImageResponse(
    val content_id:String,
    val title: String,
    val author: String,
    val category:String,
    val media_url: List<String>,
    val thumbnail_url:String,
    val post_date: String,
    val description: String,
    val commentCount: String
)

data class ContentVideoResponse(
    val content_id:String,
    val title: String,
    val author: String,
    val category:String,
    val media_url: String,
    val thumbnail_url:String,
    val post_date: String,
    val description: String,
    val commentCount: String
)