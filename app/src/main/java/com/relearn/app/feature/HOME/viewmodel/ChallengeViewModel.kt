package com.relearn.app.feature.HOME.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.relearn.app.feature.HOME.model.*
import com.relearn.app.feature.HOME.repository.ChallengeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val repository: ChallengeRepository
) : ViewModel() {

    private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
    val challenges: StateFlow<List<Challenge>> = _challenges

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentCheckIn: DailyCheckIn? = null
    private var currentHabits: List<String> = emptyList()
    private var userId: String = ""

    val today = LocalDate.now().toEpochDay()

    init {
        Log.d("ChallengeVM", "ViewModel init!!")
    }

    fun setContext(userId: String, checkIn: DailyCheckIn, habits: List<String>) {
        this.userId = userId
        currentCheckIn = checkIn
        currentHabits = habits
        Log.d("ChallengeVM", "Context set: userId=$userId, checkIn=$checkIn, habits=$habits")
    }

    fun loadChallenges() {
        val checkIn = currentCheckIn ?: run {
            Log.d("ChallengeVM", "loadChallenges aborted: currentCheckIn is null")
            return
        }
        val habits = currentHabits
        val moodLabel = mapMoodToLabel(checkIn.mood)
        val difficulty = mapEnergyToDifficulty(checkIn.energyLevel)

        Log.d("ChallengeVM", "loadChallenges called with mood=$moodLabel, difficulty=$difficulty, habits=$habits")

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getOrGenerateUserChallengesForToday(
                    userId = userId,
                    habits = habits,
                    difficulty = difficulty,
                    mood = moodLabel,
                    energy = checkIn.energyLevel
                ).toMutableList()

                val journalExtras = repository.getJournalChallenges()
                result.addAll(journalExtras)

                val filtered = result.filter { it.status != ChallengeStatus.COMPLETED }
                _challenges.value = filtered
                Log.d("ChallengeVM", "Filtered active challenges: ${filtered.size}")
                _error.value = null
                Log.d("ChallengeVM", "Challenges loaded: ${result.size}")
            } catch (e: Exception) {
                Log.e("ChallengeVM", "Error loading challenges", e)
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshChallenges() {
        if (currentCheckIn != null && currentHabits.isNotEmpty()) {
            Log.d("ChallengeVM", "refreshChallenges called")
            loadChallenges()
        } else {
            Log.d("ChallengeVM", "refreshChallenges skipped: incomplete context")
        }
    }

    fun completeChallenge(challengeId: String) {
        viewModelScope.launch {
            val updatedList = _challenges.value.toMutableList()
            val index = updatedList.indexOfFirst { it.id == challengeId }
            if (index != -1) {
                val target = updatedList[index]
                if (target.categorie.equals("auto", ignoreCase = true)) {
                    repository.deleteUserChallenge(userId, today, challengeId)
                    Log.d("ChallengeVM", "Deleted user challenge $challengeId, reloading challenges")
                    loadChallenges()
                } else {
                    repository.markChallengeCompleted(challengeId)
                    updatedList[index] = target.copy(status = ChallengeStatus.COMPLETED)
                    _challenges.value = updatedList
                    Log.d("ChallengeVM", "Marked challenge completed: $challengeId")
                }
            } else {
                Log.d("ChallengeVM", "completeChallenge: challengeId $challengeId not found in current list")
            }
        }
    }

    fun skipChallenge(challengeId: String) {
        viewModelScope.launch {
            Log.d("ChallengeVM", "skipChallenge called for $challengeId")
            val replacement = repository.regenerateChallenge(challengeId)
            if (replacement != null) {
                Log.d("ChallengeVM", "Replacement found: ${replacement.title}")
                _challenges.value = _challenges.value.map {
                    if (it.id == challengeId) replacement else it
                }
            } else {
                Log.d("ChallengeVM", "No replacement found for $challengeId")
            }
        }
    }

    private fun mapEnergyToDifficulty(energy: Int): DifficultyLevel {
        return when {
            energy <= 3 -> DifficultyLevel.STARTER
            energy in 4..7 -> DifficultyLevel.FOCUS
            else -> DifficultyLevel.FLOW
        }
    }

    private fun mapMoodToLabel(mood: String): String = when (mood.uppercase()) {
        "SAD" -> "Trist"
        "HAPPY" -> "Fericit"
        "ANXIOUS" -> "Anxios"
        "CALM" -> "Calm"
        "ANGRY" -> "Furios"
        "STRESSED" -> "Stresat"
        else -> mood
    }

    fun removeChallengeFromUI(challengeId: String) {
        _challenges.value = _challenges.value.filterNot { it.id == challengeId }
        Log.d("ChallengeVM", "Removed challenge from UI: $challengeId")
    }
}
