// ChallengeCard.kt
package com.relearn.app.feature.HOME.ui.components

import androidx.compose.runtime.Composable
import com.relearn.app.feature.HOME.model.Challenge

@Composable
fun ChallengeCard(
    challenge: Challenge,
    onToggleCompleted: () -> Unit,
    onSkip: () -> Unit
) {
    ChallengeItem(
        challenge = challenge,
        onComplete = onToggleCompleted,
        onSkip = onSkip
    )
}
