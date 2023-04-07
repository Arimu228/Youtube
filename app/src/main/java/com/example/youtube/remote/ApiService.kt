package com.example.youtube.remote

import com.example.youtube.model.Playlist
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
    fun getPlaylists(
        @Query("key") key:String,
        @Query("part") part:String,
        @Query("channelId") channelId:String,
        ) : Call<Playlist>
}