package com.example.youtube.ui

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.data.remote.model.Videos

class MainViewModel : BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<Playlist>> {
        return App.repository.getPlaylists()
    }

    fun getPlaylistItem(playlistId: String?): LiveData<Resource<PlaylistItem>> {
        return App.repository.getPlaylistItem(playlistId!!)
    }

    fun getVideo(id: String): LiveData<Resource<Videos>> {
        return App.repository.getVideo(id)
    }
}