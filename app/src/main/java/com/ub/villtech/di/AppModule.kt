package com.ub.villtech.di

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.ub.villtech.VillTechDatabase
import com.ub.villtech.repository.firebase.FirebaseRepository
import com.ub.villtech.room.RoomViewModel
import com.ub.villtech.utils.BottomNavigationState
import com.ub.villtech.utils.LoginChecker
import com.ub.villtech.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.get
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
        viewModel { BottomNavigationViewModel() }
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