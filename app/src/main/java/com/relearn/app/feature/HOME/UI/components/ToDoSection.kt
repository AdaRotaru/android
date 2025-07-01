package com.relearn.app.feature.HOME.ui.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.HOME.model.PersonalTask
import androidx.compose.material3.TextFieldDefaults



@Composable
fun ToDoSection(
    userId: String,
    tasks: List<PersonalTask>,
    onAdd: (String) -> Unit,
    onToggleDone: (PersonalTask) -> Unit,
    onDelete: (String) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var newTask by remember { mutableStateOf("") }

    val doneCount = tasks.count { it.isDone }
    val totalCount = tasks.size

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Task-uri personale",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF7B1FA2),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$doneCount / $totalCount",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF9C27B0),
                modifier = Modifier.padding(end = 12.dp)
            )
            IconButton(
                onClick = { showAddDialog = true },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adaugă task", tint = Color(0xFF9C27B0))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        tasks.forEach { task ->
            key(task.id + task.isDone) {
                ToDoItem(
                    task = task,
                    onToggleDone = { onToggleDone(task) },
                    onDelete = { onDelete(task.id) }
                )
            }
        }
    }
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Adaugă task nou", color = Color(0xFF7B1FA2)) },
            text = {
                OutlinedTextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    label = { Text("Descriere task", color = Color(0xFF7B1FA2)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xFF9C27B0),
                        focusedLabelColor = Color(0xFF9C27B0)
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (newTask.isNotBlank()) {
                            onAdd(newTask)
                            newTask = ""
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Adaugă", color = Color(0xFF9C27B0))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Anulează", color = Color(0xFF9C27B0))
                }
            }
        )
    }
}