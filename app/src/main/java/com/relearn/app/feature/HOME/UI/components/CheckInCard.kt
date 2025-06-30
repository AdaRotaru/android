
package com.relearn.app.feature.HOME.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.relearn.app.feature.HOME.model.MoodType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInCard(
    onSubmit: (String, Int) -> Unit
) {
    var selectedMood by remember { mutableStateOf<MoodType?>(null) }
    var energyLevel by remember { mutableStateOf(5f) }
    var expanded by remember { mutableStateOf(false) }

    val moodOptions = MoodType.values()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = Color(0xFFFFF7FA), // roz extrem de pal
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            text = "Cum te simți astăzi?",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            ),
            color = Color(0xFF4A4A68)
        )

        Spacer(Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedMood?.label ?: "Selectează starea",
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Stare emoțională")
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color(0xFFE2DDE0),
                    focusedIndicatorColor = Color(0xFFB57BA6),
                    focusedLabelColor = Color(0xFFB57BA6),
                    focusedTrailingIconColor = Color(0xFFB57BA6)
                ),
                shape = RoundedCornerShape(16.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                moodOptions.forEach { mood ->
                    DropdownMenuItem(
                        text = { Text(mood.label) },
                        onClick = {
                            selectedMood = mood
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Nivel de energie: ${energyLevel.toInt()}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF4A4A68)
        )

        Slider(
            value = energyLevel,
            onValueChange = { energyLevel = it },
            valueRange = 0f..10f,
            steps = 9,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFB57BA6),
                activeTrackColor = Color(0xFFD7BADF)
            )
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                selectedMood?.let {
                    onSubmit(it.name, energyLevel.toInt())
                }
            },
            enabled = selectedMood != null,
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB57BA6))
        ) {
            Text("Trimite", color = Color.White)
        }
    }
}
