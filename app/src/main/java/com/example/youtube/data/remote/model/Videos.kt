package com.example.youtube.data.remote.model

data class Videos(
    val items: List<Items>,
)

data class Items(
    val contentDetails: ContentDetails,
) {
    data class ContentDetails(
        val duration: String,
    )
}
