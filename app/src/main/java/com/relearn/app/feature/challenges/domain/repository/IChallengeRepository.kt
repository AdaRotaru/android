package com.relearn.app.feature.challenges.domain.repository

import com.relearn.app.feature.challenges.domain.model.Challenge
import com.relearn.app.feature.challenges.domain.model.ChallengeStatus

interface IChallengeRepository {
    suspend fun getChallenges(): List<Challenge>
    suspend fun getChallengesByStatus(status: ChallengeStatus): List<Challenge>
    suspend fun addChallenge(challenge: Challenge)
    suspend fun updateChallenge(challenge: Challenge)
    suspend fun deleteChallenge(id: String)
    suspend fun updateChallengeStatus(id: String, status: ChallengeStatus)
}
