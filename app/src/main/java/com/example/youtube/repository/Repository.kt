package com.example.youtube.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.youtube.core.network.result.Resource
import com.example.youtube.data.remote.RemoteDataSource
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import kotlinx.coroutines.Dispatchers


class Repository {

    private val dataSource: RemoteDataSource by lazy {
        RemoteDataSource()
    }

    fun getPlaylists(): LiveData<Resource<Playlist>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getPlaylists()
            emit(response)
        }
    }
    fun getPlaylistItem(playlistId:String?): LiveData<Resource<PlaylistItem>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = dataSource.getPlaylistItem(playlistId)
            emit(response)
        }
    }
}