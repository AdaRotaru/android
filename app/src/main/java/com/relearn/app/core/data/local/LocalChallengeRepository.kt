package com.relearn.app.core.data.local

import com.relearn.app.feature.challenges.domain.model.Challenge
import com.relearn.app.feature.challenges.domain.model.ChallengeStatus
import com.relearn.app.feature.challenges.domain.repository.IChallengeRepository

class LocalChallengeRepository : IChallengeRepository {

    override suspend fun getChallenges(): List<Challenge> = emptyList()

    override suspend fun getChallengesByStatus(status: ChallengeStatus): List<Challenge> = emptyList()

    override suspend fun addChallenge(challenge: Challenge) {}

    override suspend fun updateChallenge(challenge: Challenge) {}

    override suspend fun deleteChallenge(id: String) {}

    override suspend fun updateChallengeStatus(id: String, status: ChallengeStatus) {}
}
