package com.example.youtube.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.data.remote.model.Item
import com.example.youtube.data.remote.model.PlaylistItem
import com.example.youtube.databinding.ActivityDetailBinding
import com.example.youtube.ui.MainViewModel
import com.example.youtube.ui.player.PlayerActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, MainViewModel>() {

    private lateinit var adapter: DetailAdapter

    override val viewModel:  MainViewModel by viewModel()

    override fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun initViews() {
        super.initViews()
        adapter = DetailAdapter(this::onClick)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun initViewModel() {
        super.initViewModel()
        val getId = intent.getStringExtra("id")
        val getTitle = intent.getStringExtra("title")
        val getDesc = intent.getStringExtra("desc")
        val getCount = intent.getIntExtra("count" ,0)

        viewModel.getPlaylistItem(getId).observe(this) {
            it.data?.let { it1 -> adapter.addList(it1.items) }
            binding.tvTitle.text = getTitle
            binding.tvDesc.text = getDesc
            binding.tvCounterVideo.text = "$getCount video series"
        }

    }

    override fun isConnection() {
        super.isConnection()
        if (!isInternetAvailable()) {
            binding.checkInternet.failInternet.isVisible = true
        }
    }

    override fun initListener() {
        super.initListener()
        binding.imgBack.setOnClickListener { finish() }
        binding.tvBack.setOnClickListener { finish() }
    }

    override fun inflateViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }
    private fun onClick(item: PlaylistItem.Item) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra("id", item.id)
        intent.putExtra("title", item.snippet?.title)
        intent.putExtra("desc", item.snippet?.description)
        startActivity(intent)
    }


}