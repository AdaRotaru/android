package com.relearn.app.ui.challenge

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import com.relearn.app.feature.challenges.domain.model.Challenge
import com.relearn.app.feature.challenges.domain.model.ChallengeStatus
import com.relearn.app.feature.challenges.viewmodel.ChallengeViewModel
import kotlinx.coroutines.launch
import java.util.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChallengeScreen(
    viewModel: ChallengeViewModel,
    onViewChallengesClick: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val status = ChallengeStatus.PENDING

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    val categories = listOf("Mindfulness", "Fitness", "Productivity", "Other...")
    var selectedCategory by remember { mutableStateOf(categories.first()) }
    var customCategory by remember { mutableStateOf("") }
    val isCustomCategory = selectedCategory == "Other..."
    var expanded by remember { mutableStateOf(false) }

    var showErrors by remember { mutableStateOf(false) }
    val isValid = title.isNotBlank() && description.isNotBlank() && (!isCustomCategory || customCategory.isNotBlank())


    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Titlu") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            if (showErrors && title.isBlank()) {
                Text("Titlul nu poate fi gol", color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descriere") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            if (showErrors && description.isBlank()) {
                Text("Descrierea nu poate fi goală", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categorie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            if (isCustomCategory) {
                OutlinedTextField(
                    value = customCategory,
                    onValueChange = { customCategory = it },
                    label = { Text("Categorie personalizată") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }

            Button(
                onClick = {
                    showErrors = true
                    if (!isValid) return@Button
                    val finalCategory = if (isCustomCategory && customCategory.isNotBlank()) customCategory else selectedCategory
                    val challenge = Challenge(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        description = description,
                        status = status,
                        categorie = finalCategory
                    )
                    title = ""
                    description = ""
                    customCategory = ""
                    selectedCategory = categories.first()
                    showErrors = false
                    coroutineScope.launch {
                        keyboardController?.hide()
                        viewModel.addChallenge(challenge)
                        snackbarHostState.showSnackbar("✅Challenge adăugat cu succes!")
                    }
                },
                enabled = isValid,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Adaugă Challenge")
            }

            Button(
                onClick = onViewChallengesClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Vezi Challenge-uri")
            }
        }
    }
}
