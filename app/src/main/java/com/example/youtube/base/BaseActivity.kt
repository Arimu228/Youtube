package com.example.youtube.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding,VM : BaseViewModel>: AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract fun inflateViewBinding() : VB
    protected abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding()
        setContentView(binding.root)
        isConnection()
        initViews()
        initRecycler()
        initViewModel()
        initListener()
    }
   open fun initRecycler() {}
    open fun isConnection() {}
    open fun initViews() {}
    open fun initListener() {}
    open fun initViewModel() {}
}