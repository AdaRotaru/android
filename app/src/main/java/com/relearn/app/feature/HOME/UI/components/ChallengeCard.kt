package com.relearn.app.feature.HOME.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.HOME.model.Challenge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay

@Composable
fun ChallengeCard(
    challenge: Challenge,
    onToggleCompleted: () -> Unit,
    onSkip: () -> Unit,
    onRemove: (() -> Unit)? = null
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = {
                showDialog = false
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🎉 Felicitări!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF7B1FA2)
                    )
                    Text(
                        text = "Ai finalizat provocarea cu succes!",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }

        LaunchedEffect(Unit) {
            delay(2000)
            onRemove?.invoke()
            showDialog = false
        }
    } else {
        ChallengeItem(
            challenge = challenge,
            onComplete = {
                showDialog = true
                onToggleCompleted()
            },
            onSkip = onSkip,
            isCompleted = false,
            onDelete = null
        )
    }
}
