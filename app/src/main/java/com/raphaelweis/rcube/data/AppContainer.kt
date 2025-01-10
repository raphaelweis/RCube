package com.raphaelweis.rcube.data

import android.content.Context

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
