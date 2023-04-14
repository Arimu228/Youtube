package com.example.youtube.ui.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {

    private lateinit var adapter: DetailAdapter
    override val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }

    override fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun initViews() {
        super.initViews()
        adapter = DetailAdapter()
        binding.videosRv.layoutManager = GridLayoutManager(this, 1)
        binding.videosRv.adapter = adapter
    }

    override fun initViewModel() {
        super.initViewModel()
        val getId = intent.getStringExtra("id")

        viewModel.getPlaylistItem(getId).observe(this) {
            it.data?.let { it1 -> adapter.addList(it1.items) }
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
    }

    override fun inflateViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

}