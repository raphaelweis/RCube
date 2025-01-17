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
import com.raphaelweis.rcube.ui.AppViewModelProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerDestination() {
    val timerViewModel: TimerViewModel = viewModel(factory = AppViewModelProvider.Factory)

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
                    if (timerViewModel.isSolving.value) {
                        timerViewModel.stopTimer()
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
                        timerViewModel.startTimer()
                    }
                })
            }, contentAlignment = Alignment.Center, content = {
            if (!timerViewModel.isSolving.value) Scramble(
                paddingValues, viewModel = timerViewModel
            )
            Timer(
                paddingValues, timerColor, viewModel = timerViewModel
            )
            if (!timerViewModel.isSolving.value) Card(
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
                        SolveStat(text = "Ao5: 10.71")
                        SolveStat(text = "Ao12: 10.56")
                        SolveStat(text = "Ao100: 11.12")
                    }
                    ScrambleImage()
                    Column(
                        horizontalAlignment = Alignment.End,
                        modifier = Modifier.weight(1f)
                    ) {
                        SolveStat(text = "Best: 6.47")
                        SolveStat(text = "Mean: 11.68")
                        SolveStat(text = "Count: 2862")
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