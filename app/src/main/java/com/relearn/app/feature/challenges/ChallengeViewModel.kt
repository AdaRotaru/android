package com.relearn.app.feature.challenges

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val repository: IChallengeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChallengeUiState())
    val uiState: StateFlow<ChallengeUiState> = _uiState

    fun loadChallengesByStatus(status: ChallengeStatus) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val challenges = repository.getChallengesByStatus(status)
                _uiState.value = _uiState.value.copy(
                    challenges = challenges,
                    selectedStatus = status,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                )
            }
        }
    }

    fun addChallenge(challenge: Challenge) {
        viewModelScope.launch {
            repository.addChallenge(challenge)
            loadChallengesByStatus(_uiState.value.selectedStatus)
        }
    }

    fun updateChallenge(challenge: Challenge) {
        viewModelScope.launch {
            repository.updateChallenge(challenge)
            loadChallengesByStatus(_uiState.value.selectedStatus)
        }
    }

    fun deleteChallenge(challengeId: String) {
        viewModelScope.launch {
            repository.deleteChallenge(challengeId)
            loadChallengesByStatus(_uiState.value.selectedStatus)
        }
    }

    fun updateChallengeStatus(challengeId: String, newStatus: ChallengeStatus) {
        viewModelScope.launch {
            repository.updateChallengeStatus(challengeId, newStatus)
            loadChallengesByStatus(newStatus)
        }
    }

    fun setStatusFilter(status: ChallengeStatus) {
        loadChallengesByStatus(status)
    }
}

