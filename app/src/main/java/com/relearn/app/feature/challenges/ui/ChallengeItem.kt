package com.relearn.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.challenges.domain.model.Challenge
import com.relearn.app.feature.challenges.domain.model.ChallengeStatus

@Composable
fun ChallengeItem(
    challenge: Challenge,
    onStatusChange: (String, ChallengeStatus) -> Unit,
    onDelete: (String, ChallengeStatus) -> Unit = { _, _ -> }

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = challenge.title, style = MaterialTheme.typography.titleMedium)
            Text(text = challenge.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Status: ${challenge.status}", style = MaterialTheme.typography.labelSmall)

            Row(modifier = Modifier.padding(top = 8.dp)) {
                Button(
                    onClick = { onStatusChange(challenge.id, ChallengeStatus.COMPLETED) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Finalizează")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onDelete(challenge.id, challenge.status) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Șterge")
                }
            }
        }
    }
}
