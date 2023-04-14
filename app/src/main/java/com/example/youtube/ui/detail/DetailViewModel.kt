package com.example.youtube.ui.detail

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.data.remote.model.PlaylistItem

class DetailViewModel : BaseViewModel() {
    fun getPlaylistItem(playlistId: String?): LiveData<Resource<PlaylistItem>> {
        return App.repository.getPlaylistItem(playlistId)
    }
}