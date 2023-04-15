package com.example.youtube.ui.detail

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.data.remote.model.Items
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.databinding.ItemDetailBinding
import com.example.youtube.ui.detail.ConvertDuration.convertDuration
import com.example.youtube.ui.playlist.loadImage

class DetailAdapter :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    private var playlistItems = listOf<PlaylistItem.Item>()

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<PlaylistItem.Item?>?) {
        this.playlistItems = list as List<PlaylistItem.Item>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return playlistItems.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val playlistItem = playlistItems[position]
        val videoId = playlistItem.contentDetails?.videoId
        if (videoId != null) {
            VideoId.setVideo(videoId) { video ->
                holder.onBind(playlistItem, video)
            }
        } else {
            holder.onBind(playlistItem, null)
        }
    }

    inner class DetailViewHolder(private val binding: ItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun onBind(item: PlaylistItem.Item, videoItem: Items?) {
            binding.image.loadImage(item.snippet?.thumbnails?.standard?.url!!)
            binding.titleTv.text = item.snippet.title
            if (videoItem != null) {
                val duration = convertDuration(videoItem.contentDetails.duration)
                binding.durationTv.text = duration
                Log.e("com.example.youtube.ui.detail.DetailAdapter", "onBind: ${(videoItem.contentDetails)}")
            }
        }
    }
}

object VideoId {
    fun setVideo(id: String, onResult: (Items?) -> Unit) {
        App.repository.getVideo(id).observeForever { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    val video = resource.data?.items?.getOrNull(0)
                    onResult(video)
                }
                else -> {
                    onResult(null)
                }
            }
        }
    }
}

object ConvertDuration {
    fun convertDuration(duration: String): String {
        val regex = "^PT(\\d+)M(\\d+)S$".toRegex()
        val matchResult = regex.find(duration)
        if (matchResult != null && matchResult.groupValues.size == 3) {
            val minutes = matchResult.groupValues[1].toInt()
            val seconds = matchResult.groupValues[2].toInt()
            return "$minutes:${String.format("%02d", seconds)}"
        }
        return ""
    }
}

