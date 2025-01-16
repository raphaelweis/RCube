package com.raphaelweis.rcube.ui.destinations.profile

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.raphaelweis.rcube.R

@Composable
fun DeleteProfileDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(icon = {
        Icon(
            painter = painterResource(R.drawable.account_box_filled),
            contentDescription = stringResource(R.string.profile)
        )
    }, title = {
        Text(text = stringResource(R.string.delete_profile_dialog_title))
    }, text = {
        Text(text = stringResource(R.string.delete_profile_dialog_body))
    }, onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = onConfirm) {
            Text(stringResource(R.string.delete_profile))
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(stringResource(R.string.cancel))
        }
    })
}
