package com.example.youtube.data.remote

import com.example.youtube.BuildConfig
import com.example.youtube.core.network.BaseDataSource
import com.example.youtube.utils.Const
import org.koin.dsl.module

val remoteDataSource = module {
    factory { RemoteDataSource(get()) }
}
class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {



    suspend fun getPlaylists() = getResult {
        apiService.getPlaylists(
            BuildConfig.API_KEY,
            Const.part,
            Const.channelIdEdSheeran,
            20
        )
    }

    suspend fun getPlaylistItem(playlistId: String?) = getResult {
        apiService.getPlaylistItem(
            BuildConfig.API_KEY,
            Const.part,
            playlistId!!,
            20
        )
    }

    suspend fun getVideo(id: String?) = getResult {
        apiService.getVideo(
            BuildConfig.API_KEY,
            Const.part,
            id!!
        )
    }
}