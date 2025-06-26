package com.relearn.app.feature.challenges

data class ChallengeUiState(
    val challenges: List<Challenge> = emptyList(),
    val selectedStatus: ChallengeStatus = ChallengeStatus.PENDING,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
