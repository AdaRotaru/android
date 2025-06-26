package com.relearn.app.feature.offline.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    // Observăm toate obiceiurile din baza de date
    val allHabits: StateFlow<List<HabitEntity>> =
        repository.allHabits
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Adaugă un obicei nou
    fun addHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.insert(habit)
        }
    }

    // Șterge un obicei
    fun deleteHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.delete(habit)
        }
    }

    // Actualizează un obicei existent
    fun updateHabit(habit: HabitEntity) {
        viewModelScope.launch {
            repository.update(habit)
        }
    }
}
