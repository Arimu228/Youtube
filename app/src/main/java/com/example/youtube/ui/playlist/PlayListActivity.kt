package com.example.youtube.ui.playlist

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.youtube.base.BaseActivity
import com.example.youtube.databinding.ActivityPlaylistBinding
import com.example.youtube.model.Item
import com.example.youtube.ui.MainViewModel
import com.example.youtube.ui.WatchActivity

class PlayListActivity : BaseActivity<ActivityPlaylistBinding, MainViewModel>() {

    private lateinit var adapter: PlayListAdapter


    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun initRecycler() {
        super.initRecycler()
        adapter = PlayListAdapter(this::onClick)
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.playlists().observe(this){
            binding.recyclerView.layoutManager = GridLayoutManager(this,1)
            binding.recyclerView.adapter = adapter
            adapter.addList(it.items)
        }
    }
    override fun inflateViewBinding(): ActivityPlaylistBinding {
        return ActivityPlaylistBinding.inflate(layoutInflater)
    }

    private fun onClick(item: Item) {
        val intent = Intent(this, WatchActivity::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)
    }
}
