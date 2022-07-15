//package com.ub.villtech.room
//
//import android.app.Application
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//
//class RoomViewModelFactory (val application: Application):ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return RoomViewModel(application = application) as T
//    }
//}