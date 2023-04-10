package com.example.youtube.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.BuildConfig
import com.example.youtube.core.network.RetrofitClient
import com.example.youtube.core.network.result.Resource
import com.example.youtube.data.remote.ApiService
import com.example.youtube.data.remote.model.Playlist
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    private val apiService: ApiService = RetrofitClient.create()

     fun getPlaylist(): LiveData<Resource<Playlist>> {
        val data = MutableLiveData<Resource<Playlist>>()
        apiService.getPlaylists(BuildConfig.API_KEY, "contentDetails,snippet", "UCzy6RoMcGa42SkiYJekudQw")
            .enqueue(
                object : Callback<Playlist> {
                    override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                        if (response.isSuccessful) {
                            data.value = Resource.success(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<Playlist>, t: Throwable) {
                        data.value = t.message?.let { Resource.error(it) }
                    }

                }
            )
        return data
    }
}