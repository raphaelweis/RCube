package com.raphaelweis.rcube.ui.destinations.timer

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
        })
    })
}
