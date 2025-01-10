package com.raphaelweis.rcube.ui.destinations.profile.timer

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.raphaelweis.rcube.R

@Composable
fun BoxScope.Scramble(
    paddingValues: PaddingValues,
    viewModel: TimerViewModel,
) {
    if (viewModel.currentScramble.value != "") {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(paddingValues)
                .padding(horizontal = Dp(12F))
        ) {
            Text(
                text = viewModel.currentScramble.value,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { viewModel.getNewScramble() }) {
                Icon(
                    painter = painterResource(R.drawable.cycle_outlined),
                    contentDescription = stringResource(R.string.cycle_description)
                )
            }
        }
    } else {
        LinearProgressIndicator(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = Dp(4F))
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
    }
}