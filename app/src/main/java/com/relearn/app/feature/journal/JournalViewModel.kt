package com.relearn.app.feature.journal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.relearn.app.feature.HOME.model.Challenge
import com.relearn.app.feature.HOME.model.ChallengeStatus
import com.relearn.app.feature.HOME.model.DifficultyLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    private val journalRepository: JournalInterface
) : ViewModel() {

    private val _journalEntry = MutableStateFlow<JournalEntry?>(null)
    val journalEntry: StateFlow<JournalEntry?> = _journalEntry

    private val _journalDraft = MutableStateFlow("")
    val journalDraft: StateFlow<String> = _journalDraft

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _confirmation = MutableStateFlow<String?>(null)
    val confirmation: StateFlow<String?> = _confirmation

    private val firestore = FirebaseFirestore.getInstance()
    private val challengesCollection = firestore.collection("challenges")

    fun submitJournal(content: String) {
        viewModelScope.launch {
            Log.d("JournalVM", "Submitting: $content")
            _loading.value = true
            _error.value = null
            try {
                val entry = journalRepository.submitJournalEntry(content)
                _journalEntry.value = entry
                // NU curăța _journalDraft aici dacă vrei să păstrezi textul
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("JournalVM", "Eroare la submitJournal: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateDraft(text: String) {
        _journalDraft.value = text
    }

    fun clearState() {
        _journalEntry.value = null
        _error.value = null
    }

    fun confirm(message: String) {
        _confirmation.value = message
        viewModelScope.launch {
            delay(2000)
            _confirmation.value = null
        }
    }

    fun removeSuggestion(suggestion: String) {
        _journalEntry.value = _journalEntry.value?.copy(
            suggestions = _journalEntry.value?.suggestions?.filterNot { it == suggestion } ?: emptyList()
        )
    }

    fun addSuggestionAsChallenge(suggestion: String) {
        viewModelScope.launch {
            val challenge = Challenge(
                id = UUID.randomUUID().toString(),
                title = "Provocare din jurnal",
                description = suggestion,
                habitTarget = "General",
                mood = "General",
                nivel = DifficultyLevel.STARTER,
                status = ChallengeStatus.PENDING,
                timestamp = System.currentTimeMillis(),
                activ = true,
                categorie = "Journal"
            )
            challengesCollection.document(challenge.id).set(challenge).addOnSuccessListener {
                confirm("Provocarea a fost adăugată")
                removeSuggestion(suggestion)
            }
        }
    }
}
