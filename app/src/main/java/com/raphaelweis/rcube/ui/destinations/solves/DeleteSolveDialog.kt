package com.raphaelweis.rcube.ui.destinations.solves

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.raphaelweis.rcube.R

@Composable
fun DeleteSolveDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(icon = {
        Icon(
            painter = painterResource(R.drawable.delete_outlined),
            contentDescription = stringResource(R.string.delete_solve)
        )
    }, title = {
        Text(text = stringResource(R.string.delete_solve_confirmation_title))
    }, text = {
        Text(text = stringResource(R.string.delete_solve_confirmation_description))
    }, onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = onConfirm) {
            Text(stringResource(R.string.delete))
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(stringResource(R.string.dismiss))
        }
    })
}
