package com.raphaelweis.rcube.ui.destinations.solves

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raphaelweis.rcube.data.entities.Solve
import com.raphaelweis.rcube.data.repositories.SolvesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SolvesViewModel(private val solvesRepository: SolvesRepository) : ViewModel() {
    val solvesUiState: StateFlow<SolvesUiState> =
        solvesRepository.getAllSolvesStream().map { SolvesUiState(it) }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SolvesUiState()
        )

    private val solvesRepositoryScope = CoroutineScope(Dispatchers.Default)

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun deleteSolve(id: Int) {
        solvesRepositoryScope.launch {
            solvesRepository.deleteSolve(id)
        }
    }
}

data class SolvesUiState(val solvesList: List<Solve> = listOf())