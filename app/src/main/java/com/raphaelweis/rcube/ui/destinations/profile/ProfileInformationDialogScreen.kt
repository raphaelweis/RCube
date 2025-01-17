package com.raphaelweis.rcube.ui.destinations.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.raphaelweis.rcube.MainNavigationScaffold
import com.raphaelweis.rcube.R
import com.raphaelweis.rcube.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInformationDialogScreen(
    mainNavController: NavHostController,
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val username = viewModel.username.collectAsState().value
    val birthdate = viewModel.birthdate.collectAsState().value
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    keyboardController?.hide()
                    mainNavController.popBackStack()
                }
            }) {
                Icon(
                    painter = painterResource(R.drawable.close_outlined),
                    contentDescription = "hello",
                    modifier = Modifier.size(24.dp)
                )
            }
        }, actions = {
            TextButton(
                content = { Text(stringResource(R.string.save)) },
                onClick = {
                    scope.launch {
                        if (viewModel.saveUser()) {
                            keyboardController?.hide()
                            mainNavController.navigate(MainNavigationScaffold(screenId = 2))
                        }
                    }
                },
            )
        }, title = { Text(stringResource(R.string.profile_information)) })
    }) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Text(stringResource(R.string.profile_information_dialog_info))
            OutlinedTextField(
                value = username,
                onValueChange = { viewModel.updateUsername(it) },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = birthdate,
                singleLine = true,
                onValueChange = { viewModel.updateBirthdate(it) },
                label = { Text(stringResource(R.string.date_of_birth)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
