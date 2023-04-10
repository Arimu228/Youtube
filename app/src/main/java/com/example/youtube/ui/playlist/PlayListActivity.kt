package com.example.youtube.ui.playlist

import android.content.Intent
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityPlaylistBinding
import com.example.youtube.data.remote.model.Item
import com.example.youtube.ui.PlayListViewModel
import com.example.youtube.ui.detail.WatchActivity

class PlayListActivity : BaseActivity<ActivityPlaylistBinding, PlayListViewModel>() {

    private lateinit var adapter: PlayListAdapter


    override val viewModel: PlayListViewModel by lazy {
        ViewModelProvider(this)[PlayListViewModel::class.java]
    }

    override fun initViews() {
        super.initViews()
        adapter = PlayListAdapter(this::onClick)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView.adapter = adapter
    }

    override fun initViewModel() {
        super.initViewModel()
        viewModel.loading.observe(this) {
            binding.progressBar.isVisible = it

        }
        viewModel.getPlaylists().observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { it1 -> adapter.addList(it1.items) }
                    viewModel.loading.postValue(false)
                }
                Resource.Status.LOADING -> {
                    viewModel.loading.postValue(true)
                }
                Resource.Status.ERROR -> {
                    viewModel.loading.postValue(false)
                }
            }
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
