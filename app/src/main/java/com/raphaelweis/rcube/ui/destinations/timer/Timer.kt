package com.raphaelweis.rcube.ui.destinations.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.formatSolveTime
import com.raphaelweis.rcube.ui.AppViewModelProvider
import com.raphaelweis.rcube.ui.destinations.solves.DeleteSolveDialog

const val TIMER_DELAY: Long = 200
const val TIMER_UPDATE_INTERVAL: Long = 10

@Composable
fun Timer(
    padding: PaddingValues,
    timerColor: Color,
    viewModel: TimerViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val currentSolve = viewModel.currentSolve.value

    val showDeleteDialog = remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = if (currentSolve?.dnf == true) "DNF" else if (currentSolve?.plusTwo == true) "${
                formatSolveTime(viewModel.elapsedTime.longValue + 2000)
            } +" else formatSolveTime(viewModel.elapsedTime.longValue),
            color = timerColor,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(padding)
        )
        if (viewModel.elapsedTime.longValue != 0.toLong() && !viewModel.isSolving.value) {
            Spacer(Modifier.height(8.dp))
            Row {
                IconButton(enabled = currentSolve?.dnf == false,
                    onClick = { viewModel.togglePlusTwo() }) {
                    if (currentSolve?.plusTwo == true) {
                        Icon(
                            painter = painterResource(R.drawable.undo_outlined),
                            contentDescription = stringResource(R.string.undo)
                        )
                    } else {
                        Text("+2", fontWeight = FontWeight.SemiBold)
                    }
                }
                IconButton(enabled = currentSolve?.plusTwo == false,
                    onClick = { viewModel.toggleDnf() }) {
                    if (currentSolve?.dnf == true) {
                        Icon(
                            painter = painterResource(R.drawable.undo_outlined),
                            contentDescription = stringResource(R.string.undo)
                        )
                    } else {
                        Text("DNF", fontWeight = FontWeight.W500)
                    }
                }
                IconButton(onClick = { showDeleteDialog.value = true }) {
                    Icon(
                        painter = painterResource(R.drawable.delete_outlined),
                        contentDescription = stringResource(R.string.delete_solve),
                    )
                }
            }
        }
    }

    if (showDeleteDialog.value) {
        DeleteSolveDialog(onDismiss = { showDeleteDialog.value = false }, onConfirm = {
            viewModel.deleteCurrentSolve()
            showDeleteDialog.value = false
        })
    }
}


