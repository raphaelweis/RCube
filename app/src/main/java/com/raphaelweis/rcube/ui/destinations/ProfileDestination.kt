package com.raphaelweis.rcube.ui.destinations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ShoppingDestination() {
    Scaffold(content = { padding ->
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            content = {
                Text("Profile", modifier = Modifier.padding(padding))
            })
    })
}