package com.raphaelweis.rcube.ui.destinations.profile.solves

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Solve() {
    Card(
        shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row() {
                Text(
                    text = "8.79",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = SimpleDateFormat(
                        "MM/dd", Locale.getDefault()
                    ).format(Date()),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Top)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "B' U2 R D' B' D L2 U R' U2 B2 D2 F2 U F2 B2 U R2 B2",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
