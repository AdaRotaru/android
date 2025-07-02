package com.relearn.app.feature.HOME.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.HOME.model.Challenge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutHorizontally

@Composable
fun ChallengeItem(
    challenge: Challenge,
    onComplete: () -> Unit,
    onSkip: () -> Unit,
    isCompleted: Boolean = false,
    onDelete: (() -> Unit)? = null
) {
    Log.d("ChallengeItem", "Rendering challenge: ${challenge.id}")
    Log.d("ChallengeItem", "Categorie: ${challenge.categorie}, Title: ${challenge.title}, Status: ${challenge.status}, Description: ${challenge.description}, isCompleted=$isCompleted")

    val bgColor = if (isCompleted) Color(0xFFE2DDE0) else Color(0xFFFFEBF8)

    AnimatedVisibility(
        visible = !isCompleted,
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = challenge.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF4A4A68)
                        )
                        if (isCompleted && challenge.categorie == "Auto" && onDelete != null) {
                            IconButton(onClick = {
                                Log.d("ChallengeItem", "Delete clicked for challenge ${challenge.id}")
                                onDelete()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Șterge provocarea",
                                    tint = Color(0xFF8C5A96)
                                )
                            }
                        }
                    }

                    if (!isCompleted) {
                        if (!challenge.description.isNullOrBlank()) {
                            Log.d("ChallengeItem", "Showing description for challenge ${challenge.id}: ${challenge.description}")
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
                        } else {
                            Log.d("ChallengeItem", "No description for challenge ${challenge.id}")
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (challenge.categorie.lowercase() != "journal") {
                                TextButton(onClick = {
                                    Log.d("ChallengeItem", "Skip clicked for challenge ${challenge.id}")
                                    onSkip()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Altă provocare",
                                        tint = Color(0xFF807BA2)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Alta")
                                }
                            }

                            Button(
                                onClick = {
                                    Log.d("ChallengeItem", "Complete clicked for challenge ${challenge.id}")
                                    onComplete()
                                },
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
}
