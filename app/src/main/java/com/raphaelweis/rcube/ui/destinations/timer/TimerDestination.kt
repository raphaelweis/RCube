package com.raphaelweis.rcube.ui.destinations.timer

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.formatSolveTime
import com.raphaelweis.rcube.ui.AppViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerDestination(viewModel: TimerViewModel = viewModel(factory = AppViewModelProvider.Factory)) {
    val timerDelayScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val initialTimerColor = MaterialTheme.colorScheme.onSurface
    val intermediaryTimerColor = MaterialTheme.colorScheme.tertiary
    val finalTimerColor = MaterialTheme.colorScheme.primary
    var timerColor by remember { mutableStateOf(initialTimerColor) }

    var isPressed by rememberSaveable { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.three_by_three)) })
    }, content = { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    if (viewModel.isSolving.value) {
                        viewModel.stopTimer()
                        return@detectTapGestures
                    }

                    isPressed = true

                    var timerReady = false
                    timerDelayScope.launch {
                        timerColor = intermediaryTimerColor
                        delay(TIMER_DELAY)
                        if (isPressed) {
                            timerReady = true
                            timerColor = finalTimerColor
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    }

                    val isProperlyReleased = tryAwaitRelease()
                    isPressed = false
                    timerColor = initialTimerColor

                    if (isProperlyReleased && timerReady) {
                        viewModel.startTimer()
                    }
                })
            }, contentAlignment = Alignment.Center, content = {
            if (!viewModel.isSolving.value) Scramble(
                paddingValues, viewModel = viewModel
            )
            Timer(
                paddingValues, timerColor, viewModel = viewModel
            )
            if (!viewModel.isSolving.value) Card(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        SolveStat(text = "${stringResource(R.string.best)}: ${
                            viewModel.bestSolveTime.value?.let { bestSolveTime ->
                                formatSolveTime(bestSolveTime)
                            } ?: "--.--"
                        }")
                        SolveStat(text = "${stringResource(R.string.mean)}: ${
                            viewModel.averageSolveTime.value?.let { averageSolveTime ->
                                formatSolveTime(averageSolveTime)
                            } ?: "--.--"
                        }")
                        SolveStat(
                            text = "${stringResource(R.string.count)}: ${viewModel.totalSolveCount.intValue}"
                        )
                    }
                    ScrambleImage()
                    Column(
                        horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)
                    ) {
                        SolveStat(text = "${stringResource(R.string.ao5)}: ${
                            viewModel.currentAo5.value?.let { currentAo5 ->
                                formatSolveTime(currentAo5)
                            } ?: "--.--"
                        }")
                        SolveStat(text = "${stringResource(R.string.ao12)}: ${
                            viewModel.currentAo12.value?.let { currentAo12 ->
                                formatSolveTime(currentAo12)
                            } ?: "--.--"
                        }")
                        SolveStat(text = "${stringResource(R.string.ao100)}: ${
                            viewModel.currentAo100.value?.let { currentAo100 ->
                                formatSolveTime(currentAo100)
                            } ?: "--.--"
                        }")
                    }
                }
            }
        })
    })
}

@Composable
fun SolveStat(text: String) {
    Text(text = text, style = MaterialTheme.typography.labelLarge)
}