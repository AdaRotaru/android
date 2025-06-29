// ToDoSection.kt
package com.relearn.app.feature.HOME.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.relearn.app.feature.HOME.model.PersonalTask

@Composable
fun ToDoSection(
    tasks: List<PersonalTask>,
    onAdd: (String) -> Unit,
    onToggleDone: (PersonalTask) -> Unit,
    onDelete: (String) -> Unit
) {
    var newTask by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = newTask,
            onValueChange = { newTask = it },
            label = { Text("Adaugă task nou") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (newTask.isNotBlank()) {
                    onAdd(newTask)
                    newTask = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Adaugă")
            Spacer(modifier = Modifier.width(4.dp))
            Text("Adaugă")
        }

        Spacer(modifier = Modifier.height(16.dp))

        tasks.forEach { task ->
            ToDoItem(
                task = task,
                onToggleDone = { onToggleDone(task) },
                onDelete = { onDelete(task.id) }
            )
        }
    }
}
