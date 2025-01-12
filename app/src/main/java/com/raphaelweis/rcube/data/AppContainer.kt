package com.raphaelweis.rcube.data

import android.content.Context
import com.raphaelweis.rcube.data.databases.RCubeDatabase
import com.raphaelweis.rcube.data.repositories.OfflineSolvesRepository
import com.raphaelweis.rcube.data.repositories.SolvesRepository

/**
 * App Container for dependency injection
 */
interface AppContainer {
    val solvesRepository: SolvesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val solvesRepository: SolvesRepository by lazy {
        OfflineSolvesRepository(RCubeDatabase.getDatabase(context).solvesDAO())
    }
}
