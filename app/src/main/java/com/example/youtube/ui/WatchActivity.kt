package com.example.youtube.ui

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtube.base.BaseActivity
import com.example.youtube.databinding.ActivityWatchBinding

class WatchActivity : BaseActivity<ActivityWatchBinding, MainViewModel>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun initListener() {
        super.initListener()
        val getId = intent.getStringExtra("id")
        Toast.makeText(this, getId, Toast.LENGTH_SHORT).show()
    }

    override fun inflateViewBinding(): ActivityWatchBinding {
        return ActivityWatchBinding.inflate(layoutInflater)
    }
}