package com.raphaelweis.rcube.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raphaelweis.rcube.data.daos.SolvesDAO
import com.raphaelweis.rcube.data.daos.UsersDAO
import com.raphaelweis.rcube.data.entities.Solve
import com.raphaelweis.rcube.data.entities.User

@Database(entities = [Solve::class, User::class], version = 4, exportSchema = false)
abstract class RCubeDatabase : RoomDatabase() {
    abstract fun solvesDAO(): SolvesDAO
    abstract fun usersDAO(): UsersDAO

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