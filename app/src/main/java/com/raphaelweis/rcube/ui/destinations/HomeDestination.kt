package com.raphaelweis.rcube.ui.destinations

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.raphaelweis.rcube.R

const val TIMER_DELAY: Long = 200
const val TIMER_UPDATE_INTERVAL: Long = 10 // Update every 10ms for smooth display

@Composable
fun HomeDestination() {
    Scaffold(content = { padding ->
        Timer(padding)
    })
}

@Composable
fun Timer(padding: PaddingValues) {
    val timerDelayScope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current

    val initialTimerColor = MaterialTheme.colorScheme.onSurface
    val intermediaryTimerColor = MaterialTheme.colorScheme.tertiary
    val finalTimerColor = MaterialTheme.colorScheme.primary

    var isPressed by remember { mutableStateOf(false) }
    var isSolving by remember { mutableStateOf(false) }
    var timerColor by remember { mutableStateOf(initialTimerColor) }

    var elapsedTime by remember { mutableLongStateOf(0L) }
    var startTime by remember { mutableLongStateOf(0L) }

    LaunchedEffect(isSolving) {
        if (isSolving) {
            elapsedTime = 0
            startTime = System.currentTimeMillis()
            while (isSolving) {
                elapsedTime = System.currentTimeMillis() - startTime
                delay(TIMER_UPDATE_INTERVAL)
            }
        }
    }

    fun formatTime(timeMs: Long): String {
        val seconds = timeMs / 1000
        val milliseconds = timeMs % 1000
        return if (timeMs == 0L) {
            "0.00"
        } else {
            "${seconds}.${(milliseconds / 10).toString().padStart(2, '0')}"
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    if (isSolving) {
                        isSolving = false
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
                        isSolving = true
                    }
                })
            },
        contentAlignment = Alignment.Center,
        content = {
            Text(
                text = if (isSolving) stringResource(R.string.solving) else formatTime(elapsedTime),
                color = timerColor,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(padding)
            )
        }
    )
}