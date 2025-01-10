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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.ui.destinations.solves.SolvesDestination
import com.raphaelweis.rcube.ui.destinations.timer.TimerDestination
import com.raphaelweis.rcube.ui.destinations.profile.ShoppingDestination

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
fun MainNavigationSuiteScaffold() {
    val navController = rememberNavController()
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.TIMER.routeName) }

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
                    navController.navigate(destination.routeName) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }) {
        NavHost(navController = navController,
            startDestination = AppDestinations.TIMER.routeName,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) {
            composable(AppDestinations.TIMER.routeName) { TimerDestination() }
            composable(AppDestinations.SOLVES.routeName) { SolvesDestination() }
            composable(AppDestinations.PROFILE.routeName) { ShoppingDestination() }
        }
    }
}
