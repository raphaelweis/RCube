package com.raphaelweis.rcube.ui.destinations.solves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raphaelweis.rcube.ui.AppViewModelProvider

@Composable
fun SolvesDestination(viewModel: SolvesViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val solvesUiState = viewModel.solvesUiState.collectAsState()

    Scaffold(content = { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(
                items = solvesUiState.value.solvesList,
                key = { it.id }) { item ->
                Solve(
                    id = item.id,
                    time = item.time,
                    date = item.date,
                    scramble = item.scramble
                )
            }
        }
    })
}