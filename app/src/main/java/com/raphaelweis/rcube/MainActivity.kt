package com.raphaelweis.rcube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raphaelweis.rcube.ui.MainNavigationSuiteScaffold
import com.raphaelweis.rcube.ui.destinations.profile.ProfileInformationDialogScreen
import com.raphaelweis.rcube.ui.theme.RCubeTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Serializable
object MainNavigationScaffold

@Serializable
object ProfileInformationDialog

@Composable
fun MainScreen() {
    val mainNavController = rememberNavController()

    RCubeTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            NavHost(navController = mainNavController, startDestination = MainNavigationScaffold) {
                composable<MainNavigationScaffold>(enterTransition = { EnterTransition.None }) {
                    MainNavigationSuiteScaffold(
                        mainNavController = mainNavController
                    )
                }
                composable<ProfileInformationDialog>(enterTransition = {
                    slideInVertically(initialOffsetY = { it })
                }, exitTransition = { slideOutVertically(targetOffsetY = { it }) }) {
                    ProfileInformationDialogScreen(mainNavController)
                }
            }
        }
    }
}
