package com.dkmkknub.villtech.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.dkmkknub.villtech.VillTechDatabase
import com.dkmkknub.villtech.repository.firebase.FirebaseRepository
import com.dkmkknub.villtech.room.RoomViewModel
import com.dkmkknub.villtech.utils.BottomNavigationState
import com.dkmkknub.villtech.viewmodel.*
import com.dkmkknub.villtech.utils.LoginChecker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val databaseModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                VillTechDatabase::class.java,
                "VillTechDatabase"
            ).fallbackToDestructiveMigration().build()
        }

        factory { get<VillTechDatabase>().iRoomRepository() }
    }

    val appModule = module {
        single { FirebaseAuth.getInstance() }
        single { FirebaseDatabase.getInstance() }
        single { FirebaseStorage.getInstance() }
        single { FirebaseRepository(get(), get(), get()) }
        single { LoginChecker(get(), get()) }
        single { BottomNavigationState() }
    }

    val viewModelModule = module {
        viewModel { RootViewModel() }
        viewModel { AdminLoginViewModel(get()) }
        viewModel { SearchViewModel(get()) }
        viewModel { UserHomeViewModel(get()) }
        viewModel { AdminHomeViewModel(get()) }
        viewModel { FavoriteViewModel(get(), get()) }
        viewModel { DetailPostViewModel(get()) }
        viewModel { RoomViewModel(get()) }
    }
}