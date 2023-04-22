package com.example.youtube.ui.player

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.SparseArray
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.youtube.R
import com.example.youtube.core.ui.BaseActivity
import com.example.youtube.databinding.ActivityPlayerBinding
import com.example.youtube.ui.MainViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity :  BaseActivity<ActivityPlayerBinding, MainViewModel>() {

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    @SuppressLint("StaticFieldLeak")
    private fun initializePlayer() {
        player = ExoPlayer.Builder(this@PlayerActivity)
            .build()
            .also { exoPlayer ->
                binding.videoView.player = exoPlayer
            }


        val youtubeLink = "http://youtube.com/watch?v=${intent.getStringExtra("id")}"

        object: YouTubeExtractor(this) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {

                    val videoTag = 133
                    val audioTag = 140

                    val videoUri = ytFiles.get(videoTag).url
                    val audioUri = ytFiles.get(audioTag).url

                    val audioSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(audioUri))

                    val videoSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(videoUri))


                    player?.setMediaSource(MergingMediaSource(true, videoSource, audioSource), true)
                    player?.playWhenReady = playWhenReady
                    player?.seekTo(currentItem, playbackPosition)
                    player?.prepare()
                }
            }

        }.extract(youtubeLink)
    }
    override fun inflateViewBinding(): ActivityPlayerBinding {
        return ActivityPlayerBinding.inflate(layoutInflater)
    }

    override val viewModel:  MainViewModel by viewModel()
    override fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val network = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    }

    override fun isConnection() {
        super.isConnection()
        if (!isInternetAvailable()) {
            binding.checkInternet.failInternet.isVisible = true
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        val getId = intent.getStringExtra("id")
        val getTitle = intent.getStringExtra("title")
        val getDesc = intent.getStringExtra("desc")
        viewModel.getVideo(getId!!).observe(this) {
            binding.tvTitle.text = getTitle
            binding.tvDesc.text = getDesc
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
        }
    }
    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.videoView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun initListener() {
        super.initListener()
        binding.imgBack.setOnClickListener { finish() }
        binding.tvBack.setOnClickListener { finish() }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    override fun initViews() {
        super.initViews()
        val bt_fullscreen = findViewById<ImageButton>(R.id.fullscreen)

        var isFullscreen = false
        var isLandscape = false

        bt_fullscreen.setOnClickListener {
            if (!isFullscreen) {
                bt_fullscreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.baseline_fullscreen_24
                    )
                )
                if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    isLandscape = true
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                } else {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                }
            } else {
                bt_fullscreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.baseline_fullscreen_exit_24
                    //
                    )
                )
                if (isLandscape) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                } else {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                }
            }
            isFullscreen = !isFullscreen
        }
    }
}
