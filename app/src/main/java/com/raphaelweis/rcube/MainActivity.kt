package com.raphaelweis.rcube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.raphaelweis.rcube.ui.MainNavigationSuiteScaffold
import com.raphaelweis.rcube.ui.theme.RCubeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    RCubeTheme {
        MainNavigationSuiteScaffold()
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    RCubeTheme {
        MainNavigationSuiteScaffold()
    }
}
