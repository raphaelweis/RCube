package com.raphaelweis.rcube.ui.destinations.timer

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.raphaelweis.rcube.formatSolveTime

const val TIMER_DELAY: Long = 200
const val TIMER_UPDATE_INTERVAL: Long = 10

@Composable
fun Timer(
    padding: PaddingValues,
    timerColor: Color,
    viewModel: TimerViewModel,
) {
    Text(
        text = formatSolveTime(viewModel.elapsedTime.longValue),
        color = timerColor,
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.padding(padding)
    )
}

