package com.relearn.app.feature.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HabitsSelectionScreen(
    onContinue: (List<String>) -> Unit
) {
    val accentColor = Color(0xFFBA68C8)
    val backgroundColor = Color(0xFFFFF7FA)
    val textColor = Color(0xFF444444)
    val cardBackground = Color.White.copy(alpha = 0.95f)
    val buttonColor = Color(0xFFAB47BC)

    val habitCategories = listOf(
        "🧠 Stres & emoții" to listOf("Anxietate", "Oboseală", "Auto-critică"),
        "🎯 Focus & motivație" to listOf("Procrastinare", "Lipsă de focus", "Demotivare"),
        "💤 Stil de viață" to listOf("Somn neregulat", "Sedentarism", "Mâncat impulsiv"),
        "📅 Organizare" to listOf("Haos în rutină", "Lipsă de planificare", "Gestionare slabă a timpului")
    )

    val selectedHabits = remember { mutableStateListOf<String>() }
    var showWarning by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Hai să începem cu pași mici!",
                style = MaterialTheme.typography.headlineMedium,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ce obiceiuri vrei să îmbunătățești?",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF807BA2)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${selectedHabits.size}/3 selectate",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackground)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    habitCategories.forEach { (category, habits) ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleMedium,
                            color = accentColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        habits.forEach { habit ->
                            val isSelected = habit in selectedHabits
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    if (isSelected) {
                                        selectedHabits.remove(habit)
                                    } else {
                                        if (selectedHabits.size < 3) {
                                            selectedHabits.add(habit)
                                        } else {
                                            showWarning = true
                                        }
                                    }
                                },
                                label = { Text(habit) },
                                modifier = Modifier
                                    .padding(end = 8.dp, bottom = 8.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = accentColor,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { onContinue(selectedHabits.toList()) },
                        enabled = selectedHabits.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Creează cont")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (showWarning) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x66000000))
                    .clickable(enabled = false) {}
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE3E3))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Păstrează lucrurile simple!",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFFD02541)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Alege maxim 3 obiceiuri la început pentru a rămâne focusat.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { showWarning = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF807BA2),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Ok")
                        }
                    }
                }
            }
        }
    }
}
