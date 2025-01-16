package com.raphaelweis.rcube.ui.destinations.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.raphaelweis.rcube.ProfileInformationDialog
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDestination(
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    mainNavController: NavHostController
) {
    val user = viewModel.user.collectAsState().value
    val showActivateDialog = viewModel.showActivateDialog.collectAsState().value
    val showDeleteDialog = viewModel.showDeleteDialog.collectAsState().value

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.profile)) }) }) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (user == null) {
                Text(
                    text = stringResource(R.string.you_have_not_activated_your_profile),
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.setShowActivateDialog(true)
                }) { Text(stringResource(R.string.activate_profile)) }
            } else {
                Card {
                    Icon(
                        painter = painterResource(R.drawable.account_box_filled),
                        contentDescription = stringResource(R.string.profile),
                        modifier = Modifier.size(256.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "@" + user.username,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.height(32.dp))
                Text(
                    text = user.birthdate,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(32.dp))
                Row {
                    TextButton(onClick = { viewModel.setShowDeleteDialog(true) }) {
                        Text(
                            stringResource(R.string.delete_my_data)
                        )
                    }
                    TextButton(onClick = { mainNavController.navigate(ProfileInformationDialog) }) {
                        Text(stringResource(R.string.update_my_data))
                    }
                }
            }
        }
    }

    if (showActivateDialog) ActivateProfileDialog(
        onDismiss = {
            viewModel.setShowActivateDialog(false)
        },
        onConfirm = {
            viewModel.setShowActivateDialog(false)
            mainNavController.navigate(ProfileInformationDialog)
        },
    )

    if (showDeleteDialog) DeleteProfileDialog(onDismiss = {
        viewModel.setShowDeleteDialog(false)
    }, onConfirm = {
        viewModel.setShowDeleteDialog(false)
        viewModel.deleteUser()
    })
}
