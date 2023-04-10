package com.example.youtube.ui.detail

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityWatchBinding
import com.example.youtube.ui.PlayListViewModel

class WatchActivity : BaseActivity<ActivityWatchBinding, PlayListViewModel>() {

    override val viewModel: PlayListViewModel by lazy {
        ViewModelProvider(this)[PlayListViewModel::class.java]
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