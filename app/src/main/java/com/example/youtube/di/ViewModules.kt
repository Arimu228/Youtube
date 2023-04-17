package com.example.youtube.di

import com.example.youtube.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModules = module {
    viewModel { MainViewModel(get()) }
}
