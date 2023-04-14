package com.example.youtube.data.remote

import com.example.youtube.BuildConfig
import com.example.youtube.core.network.BaseDataSource
import com.example.youtube.core.network.RetrofitClient
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.utils.Const

class RemoteDataSource : BaseDataSource() {

    private val apiService: ApiService = RetrofitClient.create()

    suspend fun getPlaylists() = getResult {
        apiService.getPlaylists(
            BuildConfig.API_KEY,
            Const.part,
            Const.channelId
        )
    }

    suspend fun getPlaylistItem(playlistId: String?) = getResult {
        apiService.getPlaylistItem(
            BuildConfig.API_KEY,
            Const.part,
            playlistId!!
        )
    }
}