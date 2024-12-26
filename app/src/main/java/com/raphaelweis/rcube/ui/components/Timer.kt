package com.raphaelweis.rcube.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.raphaelweis.rcube.R

const val TIMER_DELAY: Long = 200
const val TIMER_UPDATE_INTERVAL: Long = 10

@Composable
fun Timer(
    padding: PaddingValues,
    isSolving: Boolean,
    elapsedTime: Long,
    timerColor: Color,
) {
    fun formatTime(timeMs: Long): String {
        val seconds = timeMs / 1000
        val milliseconds = timeMs % 1000
        return if (timeMs == 0L) {
            "0.00"
        } else {
            "${seconds}.${(milliseconds / 10).toString().padStart(2, '0')}"
        }
    }

    Text(
        text = if (isSolving) stringResource(R.string.solving) else formatTime(elapsedTime),
        color = timerColor,
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.padding(padding)
    )
}

