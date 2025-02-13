@file:Suppress("KDocUnresolvedReference")

package com.raphaelweis.rcube.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.raphaelweis.rcube.RCubeApplication
import com.raphaelweis.rcube.ui.destinations.profile.ProfileViewModel
import com.raphaelweis.rcube.ui.destinations.solves.SolvesViewModel
import com.raphaelweis.rcube.ui.destinations.timer.TimerViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TimerViewModel(
                solvesRepository = rCubeApplication().container.solvesRepository,
                context = rCubeApplication().applicationContext
            )
        }
        initializer {
            SolvesViewModel(rCubeApplication().container.solvesRepository)
        }
        initializer {
            ProfileViewModel(
                usersRepository = rCubeApplication().container.usersRepository,
                context = rCubeApplication().applicationContext
            )
        }
    }
}

/**
 * Extension function that queries for [Application] object and returns an instance of
 * [RCubeApplication].
 */
fun CreationExtras.rCubeApplication(): RCubeApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RCubeApplication)
