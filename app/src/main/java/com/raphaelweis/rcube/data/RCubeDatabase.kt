package com.raphaelweis.rcube.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Solve::class], version = 1, exportSchema = false)
abstract class RCubeDatabase : RoomDatabase() {
    abstract fun solvesDAO(): SolvesDAO

    companion object {
        @Volatile
        private var Instance: RCubeDatabase? = null

        fun getDatabase(context: Context): RCubeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RCubeDatabase::class.java, "rcube_database")
                    .fallbackToDestructiveMigration().build().also { Instance = it }
            }
        }
    }
}