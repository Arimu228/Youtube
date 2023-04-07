package com.example.youtube.ui.playlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.youtube.databinding.ItemPlaylistBinding
import com.example.youtube.model.Item

class PlayListAdapter(private var list:ArrayList<Item>,private val onClick: (Item) -> Unit) :
    RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<Item>) {
        this.list = list as ArrayList<Item>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {

        return PlayListViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.onBind(list[position])
        holder.itemView.setOnClickListener {
            onClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PlayListViewHolder(private val binding: ItemPlaylistBinding) :
        ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: Item) {
            binding.imgPlay.loadImage(item.snippet.thumbnails.image.url)
            binding.tvTitle.text = item.snippet.title
            binding.tvCount.text = item.contentDetails.itemCount.toString() + "video series"
        }
    }
}