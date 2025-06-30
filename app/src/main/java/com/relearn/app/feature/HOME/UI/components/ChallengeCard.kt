
package com.relearn.app.feature.HOME.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.HOME.model.Challenge
import com.relearn.app.feature.HOME.model.ChallengeStatus

// ChallengeCard.kt
@Composable
fun ChallengeCard(
    challenge: Challenge,
    onToggleCompleted: () -> Unit,
    onSkip: () -> Unit
) {
    Column {
        ChallengeItem(
            challenge = challenge,
            onComplete = onToggleCompleted,
            onSkip = onSkip,
            isCompleted = challenge.status == ChallengeStatus.COMPLETED
        )
        if (challenge.status == ChallengeStatus.COMPLETED) {
            Text(
                text = "Felicitări pentru finalizarea acestei provocări! Continuă să te bucuri de progresul tău.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6D6D6D),
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

