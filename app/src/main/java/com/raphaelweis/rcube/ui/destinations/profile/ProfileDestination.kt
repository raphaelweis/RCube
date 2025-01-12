package com.raphaelweis.rcube.ui.destinations.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.raphaelweis.rcube.ProfileInformationDialog
import com.raphaelweis.rcube.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDestination(mainNavController: NavHostController) {
    val showActivateProfileDialog = remember { mutableStateOf(false) }

    Scaffold(topBar = { TopAppBar(title = { Text(stringResource(R.string.profile)) }) }) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(R.string.you_have_not_activated_your_profile)
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                showActivateProfileDialog.value = true
            }) { Text(stringResource(R.string.activate_profile)) }
        }
    }

    if (showActivateProfileDialog.value) ActivateProfileDialog(
        onDismiss = {
            showActivateProfileDialog.value = false
        },
        onConfirm = {
            showActivateProfileDialog.value = false
            mainNavController.navigate(ProfileInformationDialog)
        },
    )
}
