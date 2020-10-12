package com.nikpnch.canvaspaint.koin

import com.nikpnch.canvaspaint.ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ViewModel()
    }
}