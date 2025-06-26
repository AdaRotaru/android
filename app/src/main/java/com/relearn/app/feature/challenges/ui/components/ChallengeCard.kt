package com.relearn.app.feature.challenges.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.challenges.domain.model.Challenge
import com.relearn.app.feature.challenges.domain.model.ChallengeStatus
val CATEGORY_COLORS = mapOf(
    "Mindfulness" to Color(0xFFE1BEE7),
    "Fitness" to Color(0xFFB2DFDB),
    "Productivity" to Color(0xFFFFCC80),
    "Other" to Color(0xFFEF9A9A),
    "Uncategorized" to Color.Gray

)

@Composable
fun ChallengeCard(
    challenge: Challenge,
    onToggleCompleted: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val category = if (challenge.categorie.isBlank()) "Uncategorized" else challenge.categorie
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = category,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .background(
                            color = CATEGORY_COLORS[category] ?: Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Text(
                    text = challenge.nivel.name,
                    style = typography.labelSmall
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = challenge.status == ChallengeStatus.COMPLETED,
                    onCheckedChange = onToggleCompleted
                )
                Text(
                    text = challenge.title,
                    style = typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = challenge.description,
                style = typography.bodySmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Șterge")
                }
            }
        }
    }
}
