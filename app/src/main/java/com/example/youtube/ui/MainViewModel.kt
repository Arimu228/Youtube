package com.example.youtube.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.BuildConfig
import com.example.youtube.base.BaseViewModel
import com.example.youtube.model.Playlist
import com.example.youtube.remote.ApiService
import com.example.youtube.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : BaseViewModel() {

    private val apiService: ApiService = RetrofitClient.create()

    fun playlists(): LiveData<Playlist> {
        return getPlaylist()
    }

    private fun getPlaylist(): LiveData<Playlist> {
        val data = MutableLiveData<Playlist>()
        apiService.getPlaylists(BuildConfig.API_KEY, "contentDetails", "UCzy6RoMcGa42SkiYJekudQw")
            .enqueue(
                object : Callback<Playlist> {
                    override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                        if (response.isSuccessful) {
                            data.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<Playlist>, t: Throwable) {
                        Log.e("ololo", "onFailure: ${t.stackTrace}")
                    }

                }
            )
        return data
    }
}