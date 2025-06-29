package com.relearn.app.feature.challenges.domain.repository

import com.relearn.app.core.data.RemoteChallengeRepository
import com.relearn.app.core.data.LocalChallengeRepository
import com.relearn.app.feature.challenges.domain.model.Challenge
import com.relearn.app.feature.challenges.domain.model.ChallengeStatus

class DefaultChallengeRepository(
    private val remoteRepo: RemoteChallengeRepository,
    private val localRepo: LocalChallengeRepository
) : IChallengeRepository {

    override suspend fun getChallenges(): List<Challenge> {
        return try {
            remoteRepo.getChallenges()
        } catch (e: Exception) {
            localRepo.getChallenges()
        }
    }

    override suspend fun getChallengesByStatus(status: ChallengeStatus): List<Challenge> {
        return try {
            remoteRepo.getChallengesByStatus(status)
        } catch (e: Exception) {
            localRepo.getChallengesByStatus(status)
        }
    }

    override suspend fun addChallenge(challenge: Challenge) {
        try {
            remoteRepo.addChallenge(challenge)
        } catch (e: Exception) {
            localRepo.addChallenge(challenge)
        }
    }

    override suspend fun updateChallenge(challenge: Challenge) {
        try {
            remoteRepo.updateChallenge(challenge)
        } catch (e: Exception) {
            localRepo.updateChallenge(challenge)
        }
    }

    override suspend fun deleteChallenge(id: String) {
        try {
            remoteRepo.deleteChallenge(id)
        } catch (e: Exception) {
            localRepo.deleteChallenge(id)
        }
    }

    override suspend fun updateChallengeStatus(id: String, status: ChallengeStatus) {
        try {
            remoteRepo.updateChallengeStatus(id, status)
        } catch (e: Exception) {
            localRepo.updateChallengeStatus(id, status)
        }
    }
}
