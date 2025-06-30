package com.relearn.app.feature.HOME.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.relearn.app.feature.HOME.model.PersonalTask
import com.relearn.app.feature.HOME.domain.repository.PersonalTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: PersonalTaskRepository
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<PersonalTask>>(emptyList())
    val tasks: StateFlow<List<PersonalTask>> = _tasks

    fun loadTasks(userId: String) {
        viewModelScope.launch {
            val result = repository.getTasks(userId)
            Log.d("ToDoViewModel", "loadTasks result size=${result.size}")
            _tasks.value = result.toList()
        }
    }

    fun addTask(userId: String, title: String) {
        val task = PersonalTask(
            id = UUID.randomUUID().toString(),
            userId = userId,
            title = title
        )
        viewModelScope.launch {
            repository.addTask(task)
            loadTasks(userId)
        }
    }

    fun deleteTask(userId: String, taskId: String) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
            loadTasks(userId)
        }
    }

    fun toggleTaskDone(userId: String, task: PersonalTask) {
        val updatedTask = task.copy(isDone = !task.isDone)
        viewModelScope.launch {
            Log.d("ToDoViewModel", "toggleTaskDone called for task id=${task.id}, current isDone=${task.isDone}")
            repository.updateTask(updatedTask)
            loadTasks(userId) // reincarcă si emite lista actualizata
        }
    }

}
