package com.raphaelweis.rcube.ui.destinations.solves

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolvesDestination(viewModel: SolvesViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val solvesUiState = viewModel.solvesUiState.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.solves)) }) }) { paddingValues ->
        if (solvesUiState.value.solvesList.isNotEmpty()) LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, bottom = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(items = solvesUiState.value.solvesList, key = { it.id }) { item ->
                Solve(
                    id = item.id, time = item.time, date = item.date, scramble = item.scramble
                )
            }
        }
        else Box(
            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
        ) { Text(text = stringResource(R.string.no_solves)) }
    }
}