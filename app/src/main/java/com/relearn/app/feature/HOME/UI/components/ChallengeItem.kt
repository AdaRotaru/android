// ChallengeItem.kt
package com.relearn.app.feature.HOME.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.HOME.model.Challenge
import com.relearn.app.feature.HOME.model.ChallengeStatus

@Composable
fun ChallengeItem(
    challenge: Challenge,
    onComplete: () -> Unit,
    onSkip: () -> Unit,
    isCompleted: Boolean = false
) {
    val bgColor = if (isCompleted) Color(0xFFE2DDE0) else Color(0xFFFFEBF8)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = if (isCompleted) RoundedCornerShape(12.dp) else RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Titlu mereu vizibil
                Text(
                    text = challenge.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF4A4A68)
                )

                if (!isCompleted) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = challenge.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF6D6D6D)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Săgeată stânga jos
                        TextButton(onClick = onSkip) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Altă provocare",
                                tint = Color(0xFF807BA2)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Alta")
                        }

                        // Buton Finalizează
                        Button(
                            onClick = onComplete,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB57BA6))
                        ) {
                            Text("Finalizează", color = Color.White)
                        }
                    }
                }

            }
        }
    }
}
