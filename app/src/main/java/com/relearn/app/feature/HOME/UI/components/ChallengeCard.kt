
package com.relearn.app.feature.HOME.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.HOME.model.Challenge
import com.relearn.app.feature.HOME.model.ChallengeStatus


@Composable
fun ChallengeCard(
    challenge: Challenge,
    onToggleCompleted: () -> Unit,
    onSkip: () -> Unit,
    onRemove: (() -> Unit)? = null
) {
    Column {
        ChallengeItem(
            challenge = challenge,
            onComplete = onToggleCompleted,
            onSkip = onSkip,
            isCompleted = challenge.status == ChallengeStatus.COMPLETED,
            onDelete = onRemove
        )
        if (challenge.status == ChallengeStatus.COMPLETED) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Felicitări pentru finalizarea acestei provocări! Continuă să te bucuri de progresul tău.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6D6D6D),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}