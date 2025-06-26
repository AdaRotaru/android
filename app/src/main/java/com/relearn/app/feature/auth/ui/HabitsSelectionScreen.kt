package com.relearn.app.feature.auth.ui


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsSelectionScreen(
    onContinue: (List<String>) -> Unit
) {
    val habitCategories = listOf(
        "🧠 Stres & emoții" to listOf("Anxietate", "Oboseală", "Auto-critică"),
        "🎯 Focus & motivație" to listOf("Procrastinare", "Lipsă de focus", "Demotivare"),
        "💤 Stil de viață" to listOf("Somn neregulat", "Sedentarism", "Mâncat compulsiv"),
        "📅 Organizare" to listOf("Haos în rutină", "Lipsă de planificare", "Gestionare slabă a timpului")
    )

    val selectedHabits = remember { mutableStateListOf<String>() }
    var showWarning by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header fix (titlu + subtitlu + counter)
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 80.dp) // spațiu mai mare sus
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Hai să începem cu pași mici!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFD02541)
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
            }

            // Scrollabil: lista și buton
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp, bottom = 100.dp, start = 24.dp, end = 24.dp)
            ) {
                habitCategories.forEach { (category, habits) ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF4A7B9D)
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
                                selectedContainerColor = Color(0xFFB57BA6),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onContinue(selectedHabits.toList()) },
                    enabled = selectedHabits.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD02541),
                        contentColor = Color.White
                    )
                ) {
                    Text("Creează cont")
                }
            }
        }

        // Card de avertizare centrat pe ecran (indiferent de scroll)
        if (showWarning) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x66000000)) // semi-transparent overlay
                    .clickable(enabled = false) {} // previne clic pe fundal
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
