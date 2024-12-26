package com.raphaelweis.rcube.ui.components

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.raphaelweis.rcube.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.worldcubeassociation.tnoodle.scrambles.PuzzleRegistry

@Composable
fun BoxScope.Scramble(
    paddingValues: PaddingValues,
    currentScramble: String,
    onCurrentScrambleChange: (String) -> Unit,
    isSolving: Boolean
) {
    val scrambler = PuzzleRegistry.THREE.scrambler

    LaunchedEffect(currentScramble) {
        withContext(Dispatchers.Default) {
            if (currentScramble == "") onCurrentScrambleChange(scrambler.generateScramble())
        }
    }

    if (isSolving) {
        return
    }

    if (currentScramble != "") {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(paddingValues)
                .padding(horizontal = Dp(12F))
        ) {
            Text(
                text = currentScramble,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { onCurrentScrambleChange("") }) {
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