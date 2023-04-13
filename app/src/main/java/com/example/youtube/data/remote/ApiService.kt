package com.example.youtube.data.remote

import com.example.youtube.data.remote.model.Playlist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("playlists")
   suspend fun getPlaylists(
        @Query("key") key:String,
        @Query("part") part:String,
        @Query("channelId") channelId:String
        ) : Response<Playlist>
}