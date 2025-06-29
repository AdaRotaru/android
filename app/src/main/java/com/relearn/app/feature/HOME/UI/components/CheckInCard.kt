// CheckInCard.kt
package com.relearn.app.feature.HOME.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import com.relearn.app.feature.HOME.model.MoodType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckInCard(
    onSubmit: (String, Int) -> Unit
) {
    var selectedMood by remember { mutableStateOf<MoodType?>(null) }
    var energyLevel by remember { mutableStateOf(5f) }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Cum te simți astăzi?", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedMood?.label ?: "Selectează starea",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Stare emoțională") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    MoodType.values().forEach { mood ->
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

            Spacer(Modifier.height(16.dp))

            Text("Nivel de energie: ${energyLevel.toInt()}")
            Slider(
                value = energyLevel,
                onValueChange = { energyLevel = it },
                valueRange = 0f..10f,
                steps = 9,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (selectedMood != null) {
                        onSubmit(selectedMood!!.name, energyLevel.toInt())
                    }
                },
                enabled = selectedMood != null,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Trimite")
            }
        }
    }
}
