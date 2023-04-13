package com.example.youtube.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.youtube.BuildConfig
import com.example.youtube.core.network.RetrofitClient
import com.example.youtube.core.network.result.Resource
import com.example.youtube.data.remote.ApiService
import com.example.youtube.data.remote.model.Playlist
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val apiService: ApiService = RetrofitClient.create()

    fun getPlaylist(): LiveData<Resource<Playlist>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val response = apiService.getPlaylists(
                BuildConfig.API_KEY,
                "contentDetails,snippet",
                "UCzy6RoMcGa42SkiYJekudQw"
            )
            emit(
                if (response.isSuccessful) Resource.success(response.body()) else Resource.error(
                    response.message(),
                    response.body(),
                    response.code()
                )
            )
        }
    }
}