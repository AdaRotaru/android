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
    init {
        Log.d("ChallengeVM", "ViewModel init!!")
    }

    fun setContext(checkIn: DailyCheckIn, habits: List<String>) {
        currentCheckIn = checkIn
        currentHabits = habits
    }

    fun loadChallenges() {
        val checkIn = currentCheckIn ?: return
        val habits = currentHabits
        val moodLabel = mapMoodToLabel(checkIn.mood)
        val difficulty = mapEnergyToDifficulty(checkIn.energyLevel)
        Log.d("ChallengeVM", "loadChallenges called with mood=${checkIn.mood}, energy=${checkIn.energyLevel}, habits=$habits, difficulty=$difficulty")

        viewModelScope.launch {
            Log.d("ChallengeVM", "loadChallenges CALLED – context")


            _isLoading.value = true
            try {
                Log.d("ChallengeVM", "Fetching challenges with mood=${checkIn.mood}, energy=${checkIn.energyLevel}, habits=$habits, difficulty=$difficulty")

                val result = repository.getDailyChallenges(
                    habits = habits,
                    difficulty = difficulty,
                    mood = moodLabel,
                    energy = checkIn.energyLevel
                )
                Log.d("ChallengeVM", "result called with mood=${checkIn.mood}, energy=${checkIn.energyLevel}, habits=$habits, difficulty=$difficulty")

                _challenges.value = result
                Log.d("ChallengeVM", "Loaded challenges: ${result.size}")
                _error.value = null
            }catch (e: Exception) {
                Log.e("ChallengeVM", "Eroare la loadChallenges", e)
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun completeChallenge(challengeId: String) {
        viewModelScope.launch {
            repository.markChallengeCompleted(challengeId)
            loadChallenges()
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

    private fun mapMoodToLabel(mood: String): String = when(mood.uppercase()) {
        "SAD" -> "Trist"
        "HAPPY" -> "Fericit"
        "ANXIOUS" -> "Anxios"
        "CALM" -> "Calm"
        "ANGRY" -> "Furios"
        "STRESSED" -> "Stresat"
        else -> mood
    }


}
