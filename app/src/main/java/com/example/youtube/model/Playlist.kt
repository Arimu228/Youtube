package com.example.youtube.model


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
    val localized: Localized,
    val channelTitle: String,

)

data class Thumbnails(
    val default: Default,
    val high: High,
    val maxres: Maxres,
    val medium: Medium,
    val standard: Standard
)

data class Default(
    val url: String,
    val width: Int,
    val height: Int
)
data class Localized(
    val description: String,
    val title: String
)
data class Standard(
    val height: Int,
    val url: String,
    val width: Int
)
data class Medium(
    val height: Int,
    val url: String,
    val width: Int
)
data class Maxres(
    val height: Int,
    val url: String,
    val width: Int
)
data class High(
    val height: Int,
    val url: String,
    val width: Int
)
