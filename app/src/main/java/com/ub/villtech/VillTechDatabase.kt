package com.ub.villtech

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ub.villtech.repository.room.IRoomRepository
import com.ub.villtech.room.BookmarkedEntity

@Database(entities = [BookmarkedEntity::class], version = 1, exportSchema = false)
abstract class VillTechDatabase:RoomDatabase() {
    abstract fun iRoomRepository(): IRoomRepository

//    companion object {
//        private var INSTANCE: VillTechDatabase? = null
//
//        fun getInstance(context: Context): VillTechDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        VillTechDatabase::class.java, "bookmarked_content"
//                    ).fallbackToDestructiveMigration().build()
//                }
//
//                return instance
//            }
//        }
//    }
}