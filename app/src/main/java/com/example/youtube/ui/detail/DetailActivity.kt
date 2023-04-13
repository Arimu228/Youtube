package com.example.youtube.ui.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityWatchBinding
import com.example.youtube.ui.PlayListViewModel

class DetailActivity : BaseActivity<ActivityWatchBinding, PlayListViewModel>() {

    override val viewModel: PlayListViewModel by lazy {
        ViewModelProvider(this)[PlayListViewModel::class.java]
    }

    override fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun isConnection() {
        super.isConnection()
        if(!isInternetAvailable()) {
            binding.checkInternet.failInternet.isVisible = true
        }
    }

    override fun initListener() {
        super.initListener()
        val getId = intent.getStringExtra("id")
        Toast.makeText(this, getId, Toast.LENGTH_SHORT).show()
        Log.e("ololo", "initListener: $getId", )
    }

    override fun inflateViewBinding(): ActivityWatchBinding {
        return ActivityWatchBinding.inflate(layoutInflater)
    }

}