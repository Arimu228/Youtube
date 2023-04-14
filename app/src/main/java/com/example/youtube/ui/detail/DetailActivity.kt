package com.example.youtube.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityDetailBinding
import com.example.youtube.ui.MainViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding, MainViewModel>() {

    private lateinit var adapter: DetailAdapter
    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
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
        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
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
            binding.tvCounterVideo.text = getCount.toString() + "video series"
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

}