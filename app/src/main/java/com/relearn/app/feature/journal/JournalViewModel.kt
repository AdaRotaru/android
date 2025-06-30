package com.relearn.app.feature.journal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalRepository: JournalInterface
) : ViewModel() {

    private val _journalEntry = MutableStateFlow<JournalEntry?>(null)
    val journalEntry: StateFlow<JournalEntry?> = _journalEntry

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun submitJournal(content: String) {
        viewModelScope.launch {
            Log.d("JournalVM", "Submitting: $content")
            _loading.value = true
            _error.value = null
            try {
                val entry = journalRepository.submitJournalEntry(content)
                _journalEntry.value = entry
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("JournalVM", "Eroare la submitJournal: ${e.message}")

            } finally {
                _loading.value = false
            }
        }
    }

    fun clearState() {
        _journalEntry.value = null
        _error.value = null
    }
}
