package com.ub.villtech.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.ub.villtech.repository.firebase.FirebaseRepository
import com.ub.villtech.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val appModule = module {
        single { FirebaseAuth.getInstance() }
        single { FirebaseDatabase.getInstance() }
        single { FirebaseStorage.getInstance() }
        single { FirebaseRepository(get(), get(), get()) }
    }

    val viewModelModule = module {
        viewModel { BottomNavigationViewModel() }
        viewModel { RootViewModel() }
        viewModel { AdminLoginViewModel(get()) }
        viewModel { SearchViewModel(get()) }
    }
}