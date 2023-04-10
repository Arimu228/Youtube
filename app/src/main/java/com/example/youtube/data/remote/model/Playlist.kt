package com.example.youtube.data.remote.model

import com.google.gson.annotations.SerializedName


data class Playlist(
    val kind: String,
    val items: List<Item>,
    val etag: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class Item(
    val kind: String,
    val etag: String,
    val id: String,
    val contentDetails: ContentDetails,
    val snippet: Snippet
)

data class ContentDetails(
    val itemCount: Int
)

data class Snippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,

    )

data class Thumbnails(
   @SerializedName("standard")
    val image: Standard
)
data class Standard(
    val height: Int,
    val url: String,
    val width: Int
)

