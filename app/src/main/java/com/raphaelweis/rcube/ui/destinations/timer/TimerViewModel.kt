package com.raphaelweis.rcube.ui.destinations.timer

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var currentScrambleSvg = mutableStateOf("")

    var totalSolveCount = mutableIntStateOf(0)
    var bestSolveTime = mutableStateOf<Long?>(0)
    var averageSolveTime = mutableStateOf<Long?>(0)

    var currentAo5 = mutableStateOf<Long?>(null)
    var currentAo12 = mutableStateOf<Long?>(null)
    var currentAo100 = mutableStateOf<Long?>(null)

    private var formerScramble = ""
    private val scrambler = PuzzleRegistry.THREE.scrambler
    private val timerScope = CoroutineScope(Dispatchers.Default)
    private val scrambleScope = CoroutineScope(Dispatchers.Default)
    private val solvesRepositoryScope = CoroutineScope(Dispatchers.Default)
    private val preferencesHelper = PreferencesHelper(context = context)

    init {
        getNewScramble()
        loadSolveData()
    }

    private fun loadSolveData() {
        viewModelScope.launch {
            solvesRepository.getTotalSolveCount().collect { count ->
                totalSolveCount.intValue = count
            }
        }

        viewModelScope.launch {
            solvesRepository.getBestSolve().collect { time ->
                bestSolveTime.value = time
            }
        }

        viewModelScope.launch {
            solvesRepository.getAverageSolveTime().collect { averageTime ->
                averageSolveTime.value = averageTime
            }
        }

        viewModelScope.launch {
            val averageSize = 5
            solvesRepository.getLastXSolvesStream(averageSize).collect { solves ->
                if (solves.size == averageSize) {
                    currentAo5.value = computeAverageOf(solves)
                }
            }
        }

        viewModelScope.launch {
            val averageSize = 12
            solvesRepository.getLastXSolvesStream(averageSize).collect { solves ->
                if (solves.size == averageSize) {
                    currentAo12.value = computeAverageOf(solves)
                }
            }
        }

        viewModelScope.launch {
            val averageSize = 100
            solvesRepository.getLastXSolvesStream(averageSize).collect { solves ->
                if (solves.size == averageSize) {
                    currentAo100.value = computeAverageOf(solves)
                }
            }
        }
    }

    private fun computeAverageOf(solves: List<Long>): Long {
        val sortedSolves = solves.sorted()

        val withoutFirst = sortedSolves.drop(1)
        val withoutLastAndFirst = withoutFirst.dropLast(1)

        var sum: Long = 0

        for (solve in withoutLastAndFirst) sum += solve

        return sum / withoutLastAndFirst.size
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
            currentScrambleSvg.value =
                scrambler.drawScramble(currentScramble.value, null).toString()
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

            loadSolveData()
        }
    }
}
