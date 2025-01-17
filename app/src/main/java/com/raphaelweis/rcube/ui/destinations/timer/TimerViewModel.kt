package com.raphaelweis.rcube.ui.destinations.timer

import android.content.Context
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.raphaelweis.rcube.data.PreferencesHelper
import com.raphaelweis.rcube.data.entities.Solve
import com.raphaelweis.rcube.data.repositories.SolvesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry

class TimerViewModel(private val solvesRepository: SolvesRepository, context: Context) :
    ViewModel() {
    var isSolving = mutableStateOf(false)
    var elapsedTime = mutableLongStateOf(0L)
    var currentScramble = mutableStateOf("")

    private var formerScramble = ""
    private val scrambler = PuzzleRegistry.THREE.scrambler
    private val timerScope = CoroutineScope(Dispatchers.Default)
    private val scrambleScope = CoroutineScope(Dispatchers.Default)
    private val solvesRepositoryScope = CoroutineScope(Dispatchers.Default)
    private val preferencesHelper = PreferencesHelper(context = context)

    init {
        getNewScramble()
    }

    fun startTimer() {
        if (!isSolving.value) {
            val startTime = System.currentTimeMillis()
            isSolving.value = true
            elapsedTime.longValue = 0

            formerScramble = currentScramble.value
            getNewScramble()

            timerScope.launch {
                while (isSolving.value) {
                    elapsedTime.longValue = System.currentTimeMillis() - startTime
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
            val userId = preferencesHelper.getUserId()

            solvesRepository.insertSolve(
                Solve(
                    time = elapsedTime.longValue,
                    date = System.currentTimeMillis(),
                    scramble = formerScramble,
                    userId = userId
                )
            )
        }
    }
}
