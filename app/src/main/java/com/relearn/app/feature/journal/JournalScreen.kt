package com.relearn.app.feature.journal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun JournalScreen() {
    val viewModel: JournalViewModel = hiltViewModel()
    val journalEntry by viewModel.journalEntry.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val draft by viewModel.journalDraft.collectAsState()
    val confirmation by viewModel.confirmation.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF4F1))
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Jurnalul tău",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A148C),
                fontFamily = FontFamily.Serif
            )
        )

        Text(
            text = "Nu trebuie să rezolvi tot acum. Doar scrie ce simți.",
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                color = Color(0xFF4A148C)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Surface(
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 1.dp,
            color = Color(0xFFFFFBFA),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFFCE93D8), RoundedCornerShape(20.dp))
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 32.dp)
                    ) {
                        if (draft.isEmpty()) {
                            Text(
                                text = "Uneori, să pui în cuvinte e suficient.",
                                color = Color.Gray,
                                fontStyle = FontStyle.Italic
                            )
                        }
                        BasicTextField(
                            value = draft,
                            onValueChange = { viewModel.updateDraft(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 60.dp),
                            textStyle = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontSize = 15.sp,
                                color = Color.Black
                            )
                        )
                    }

                    if (draft.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            IconButton(
                                onClick = { viewModel.updateDraft("") },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Șterge",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(
                onClick = {
                    if (draft.isNotBlank()) {
                        viewModel.submitJournal(draft)
                        // draft NU este curățat aici pentru a păstra textul
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8C5A96))
            ) {
                Text("Cere o părere")
            }
        }

        if (loading) {
            CircularProgressIndicator(color = Color(0xFF8C5A96))
        }

        error?.let {
            Text(text = it, color = Color.Red)
        }

        confirmation?.let {
            Text(
                text = it,
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        journalEntry?.let { entry ->
            entry.mood?.let {
                Text(
                    text = "Stare identificată: ${it.label}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFF6A1B9A)
                    )
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "✨ Ce ai putea încerca azi?",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4A148C)
                    )
                )

                entry.suggestions.forEachIndexed { index, suggestion ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFF8C5A96)),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F3)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                val icon = when (index) {
                                    0 -> Icons.Default.NotificationsOff
                                    1 -> Icons.Default.Schedule
                                    else -> Icons.Default.Lightbulb
                                }
                                Icon(icon, contentDescription = null, tint = Color(0xFF6A1B9A))
                                Text(text = suggestion, style = TextStyle(fontSize = 14.sp))
                            }
                            Text(
                                text = "Adaugă la provocări",
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .clickable { viewModel.addSuggestionAsChallenge(suggestion) },
                                style = TextStyle(
                                    color = Color(0xFFD32F2F),
                                    fontSize = 13.sp,
                                    fontStyle = FontStyle.Italic
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
