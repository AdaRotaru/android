package com.relearn.app.feature.HOME.viewmodel

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

    fun setContext(checkIn: DailyCheckIn, habits: List<String>) {
        currentCheckIn = checkIn
        currentHabits = habits
    }

    fun loadChallenges() {
        val checkIn = currentCheckIn ?: return
        val habits = currentHabits
        val difficulty = mapEnergyToDifficulty(checkIn.energyLevel)

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getDailyChallenges(
                    habits = habits,
                    difficulty = difficulty,
                    mood = checkIn.mood,
                    energy = checkIn.energyLevel
                )
                _challenges.value = result
                _error.value = null
            } catch (e: Exception) {
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
            val replacement = repository.regenerateChallenge(challengeId)
            if (replacement != null) {
                _challenges.value = _challenges.value.map {
                    if (it.id == challengeId) replacement else it
                }
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

}
