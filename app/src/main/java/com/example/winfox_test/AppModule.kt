package com.example.winfox_test

import android.content.Context
import com.example.winfox_test.helpers.ToastHelper
import com.example.winfox_test.password.PasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { PasswordViewModel(get()) }

    single { ToastHelper(get()) }
}
