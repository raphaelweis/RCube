package com.raphaelweis.rcube.ui

import androidx.annotation.StringRes
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
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.ui.destinations.FavoritesDestination
import com.raphaelweis.rcube.ui.destinations.HomeDestination
import com.raphaelweis.rcube.ui.destinations.ShoppingDestination

enum class AppDestinations(
    @StringRes val label: Int,
    val defaultIcon: Int,
    val selectedIcon: Int,
    @StringRes val contentDescription: Int
) {
    TIMER(
        R.string.timer, R.drawable.category_outlined, R.drawable.category_filled, R.string.timer
    ),
    SOLVES(
        R.string.solves,
        R.drawable.table_rows_outlined,
        R.drawable.table_rows_filled,
        R.string.solves
    ),
    PROFILE(
        R.string.profile,
        R.drawable.account_box_outlined,
        R.drawable.account_box_filled,
        R.string.profile
    ),
}

@Composable
fun MainNavigationSuiteScaffold() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.TIMER) }

    NavigationSuiteScaffold(navigationSuiteItems = {
        AppDestinations.entries.forEach { destination ->
            val selected = destination == currentDestination

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
                onClick = { currentDestination = destination })
        }
    }) {
        when (currentDestination) {
            AppDestinations.TIMER -> HomeDestination()
            AppDestinations.SOLVES -> FavoritesDestination()
            AppDestinations.PROFILE -> ShoppingDestination()
        }
    }
}
