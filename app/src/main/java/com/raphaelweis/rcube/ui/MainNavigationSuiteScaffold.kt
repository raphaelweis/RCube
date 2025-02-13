package com.raphaelweis.rcube.ui

import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.raphaelweis.rcube.MainNavigationScaffold
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.ui.destinations.solves.SolvesDestination
import com.raphaelweis.rcube.ui.destinations.timer.TimerDestination
import com.raphaelweis.rcube.ui.destinations.profile.ProfileDestination

enum class AppDestinations(
    val routeName: String,
    @StringRes val label: Int,
    val defaultIcon: Int,
    val selectedIcon: Int,
    @StringRes val contentDescription: Int
) {
    TIMER(
        "timer",
        R.string.timer,
        R.drawable.category_outlined,
        R.drawable.category_filled,
        R.string.timer
    ),
    SOLVES(
        "solves",
        R.string.solves,
        R.drawable.table_rows_outlined,
        R.drawable.table_rows_filled,
        R.string.solves
    ),
    PROFILE(
        "profile",
        R.string.profile,
        R.drawable.account_box_outlined,
        R.drawable.account_box_filled,
        R.string.profile
    ),
}

@Composable
fun MainNavigationSuiteScaffold(
    mainNavController: NavHostController,
    backStackEntry: NavBackStackEntry
) {
    val mainNavigationScaffold: MainNavigationScaffold = backStackEntry.toRoute()
    val bottomNavController = rememberNavController()

    val startDestination: String = when (mainNavigationScaffold.screenId) {
        0 -> AppDestinations.TIMER.routeName
        1 -> AppDestinations.SOLVES.routeName
        2 -> AppDestinations.PROFILE.routeName
        else -> {
            AppDestinations.TIMER.routeName
        }
    }

    var currentDestination by rememberSaveable { mutableStateOf(startDestination) }

    NavigationSuiteScaffold(navigationSuiteItems = {
        AppDestinations.entries.forEach { destination ->
            val selected = destination.routeName == currentDestination
            item(icon = {
                Icon(
                    painter = painterResource(
                        if (selected) destination.selectedIcon
                        else destination.defaultIcon
                    ), contentDescription = stringResource(destination.contentDescription)
                )
            },
                label = { Text(stringResource(destination.label)) },
                selected = selected,
                onClick = {
                    currentDestination = destination.routeName
                    bottomNavController.navigate(destination.routeName) {
                        popUpTo(bottomNavController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }) {
        NavHost(navController = bottomNavController,
            startDestination = startDestination,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            composable(AppDestinations.TIMER.routeName) { TimerDestination() }
            composable(AppDestinations.SOLVES.routeName) { SolvesDestination() }
            composable(AppDestinations.PROFILE.routeName) { ProfileDestination(mainNavController = mainNavController) }
        }
    }
}
