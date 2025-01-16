package com.raphaelweis.rcube.data

import android.content.Context
import com.raphaelweis.rcube.data.databases.RCubeDatabase
import com.raphaelweis.rcube.data.repositories.OfflineSolvesRepository
import com.raphaelweis.rcube.data.repositories.OfflineUsersRepository
import com.raphaelweis.rcube.data.repositories.SolvesRepository
import com.raphaelweis.rcube.data.repositories.UsersRepository

/**
 * App Container for dependency injection
 */
interface AppContainer {
    val solvesRepository: SolvesRepository
    val usersRepository: UsersRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val solvesRepository: SolvesRepository by lazy {
        OfflineSolvesRepository(RCubeDatabase.getDatabase(context).solvesDAO())
    }
    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(RCubeDatabase.getDatabase(context).usersDAO())
    }
}
