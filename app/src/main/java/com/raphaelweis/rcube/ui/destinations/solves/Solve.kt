package com.raphaelweis.rcube.ui.destinations.solves

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.formatSolveTime
import com.raphaelweis.rcube.ui.AppViewModelProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Solve(
    id: Int,
    time: Long,
    date: Long,
    scramble: String,
    viewModel: SolvesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val showDeleteDialog = remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = formatSolveTime(time),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = SimpleDateFormat(
                            "MM/dd", Locale.getDefault()
                        ).format(Date(date)),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Top)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = scramble,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            IconButton(onClick = { showDeleteDialog.value = true }) {
                Icon(
                    painter = painterResource(R.drawable.delete_outlined),
                    contentDescription = stringResource(R.string.delete_solve)
                )
            }
        }
    }

    if (showDeleteDialog.value) {
        DeleteSolveDialog(
            onDismiss = { showDeleteDialog.value = false },
            onConfirm = {
                showDeleteDialog.value = false
                viewModel.deleteSolve(id)
            })
    }
}
