package com.ub.villtech.di

import com.ub.villtech.repository.Repository
import com.ub.villtech.viewmodel.AdminLoginViewModel
import com.ub.villtech.viewmodel.BottomNavigationViewModel
import com.ub.villtech.viewmodel.LoginViewModel
import com.ub.villtech.viewmodel.RootViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val appModule = module {
        single { Repository() }
    }

    val viewModelModule = module {
        viewModel { LoginViewModel(get()) }
        viewModel { BottomNavigationViewModel() }
        viewModel { RootViewModel() }
        viewModel { AdminLoginViewModel(get()) }
    }
}