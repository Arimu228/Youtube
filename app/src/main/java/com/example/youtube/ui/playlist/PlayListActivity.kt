package com.example.youtube.ui.playlist

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.youtube.base.BaseActivity
import com.example.youtube.databinding.ActivityPlaylistBinding
import com.example.youtube.model.Item
import com.example.youtube.ui.MainViewModel
import com.example.youtube.ui.WatchActivity

class PlayListActivity : BaseActivity<ActivityPlaylistBinding, MainViewModel>() {

    private lateinit var adapter: PlayListAdapter
    private var arrayList: ArrayList<Item>? = null
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun initRecycler() {
        super.initRecycler()
        if (arrayList != null) {
            adapter = PlayListAdapter(arrayList!!) {
                val intent = Intent(this, WatchActivity::class.java)
                intent.putExtra("id",it.id)
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter = adapter
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.playlists().observe(this) {
            binding.recyclerView.adapter = adapter
            adapter.addList(it.items)
        }
    }

    override fun inflateViewBinding(): ActivityPlaylistBinding {
        return ActivityPlaylistBinding.inflate(layoutInflater)
    }
}