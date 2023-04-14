package com.example.youtube.ui.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.databinding.ItemDetailBinding
import com.example.youtube.ui.playlist.loadImage

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<PlaylistItem.Item?>?) {
        this.list = list as ArrayList<PlaylistItem.Item>
        notifyDataSetChanged()
    }

    private var list = arrayListOf<PlaylistItem.Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class DetailViewHolder(private val binding: ItemDetailBinding) :
        ViewHolder(binding.root) {
        fun onBind(item: PlaylistItem.Item) {
            binding.image.loadImage(item.snippet?.thumbnails?.standard?.url!!)
            binding.titleTv.text = item.snippet.title
        }
    }
}