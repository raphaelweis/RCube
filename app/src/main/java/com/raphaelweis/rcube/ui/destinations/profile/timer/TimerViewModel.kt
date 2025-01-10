package com.raphaelweis.rcube.ui.destinations.profile.timer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.raphaelweis.rcube.data.Solve
import com.raphaelweis.rcube.data.SolvesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry

class TimerViewModel(private val solvesRepository: SolvesRepository) : ViewModel() {
    var isSolving = mutableStateOf(false)
    var elapsedTime = mutableStateOf(0L)
    var formerScramble = mutableStateOf("")
    var currentScramble = mutableStateOf("")

    private val scrambler = PuzzleRegistry.THREE.scrambler
    private val timerScope = CoroutineScope(Dispatchers.Default)
    private val scrambleScope = CoroutineScope(Dispatchers.Default)
    private val solvesRepositoryScope = CoroutineScope(Dispatchers.Default)

    init {
        getNewScramble()
    }

    fun startTimer() {
        if (!isSolving.value) {
            val startTime = System.currentTimeMillis()
            isSolving.value = true
            elapsedTime.value = 0

            formerScramble.value = currentScramble.value
            getNewScramble()

            timerScope.launch {
                while (isSolving.value) {
                    elapsedTime.value = System.currentTimeMillis() - startTime
                    delay(TIMER_UPDATE_INTERVAL)
                }
            }
        }
    }

    fun getNewScramble() {
        currentScramble.value = ""
        scrambleScope.launch {
            currentScramble.value = scrambler.generateScramble()
        }
    }

    fun stopTimer() {
        isSolving.value = false

        solvesRepositoryScope.launch {
            solvesRepository.insertSolve(
                Solve(
                    time = elapsedTime.value,
                    date = System.currentTimeMillis(),
                    scramble = formerScramble.value
                )
            )
        }
    }
}
