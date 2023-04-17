package com.example.youtube.ui

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.data.remote.model.Playlist
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.data.remote.model.Videos
import com.example.youtube.repository.Repository

class MainViewModel(private val repository: Repository) : BaseViewModel() {

    fun getPlaylists(): LiveData<Resource<Playlist>> {
        return repository.getPlaylists()
    }

    fun getPlaylistItem(playlistId: String?): LiveData<Resource<PlaylistItem>> {
        return repository.getPlaylistItem(playlistId!!)
    }

    fun getVideo(id: String): LiveData<Resource<Videos>> {
        return repository.getVideo(id)
    }
}