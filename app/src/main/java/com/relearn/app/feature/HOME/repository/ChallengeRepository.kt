package com.relearn.app.feature.HOME.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.relearn.app.feature.HOME.model.Challenge
import com.relearn.app.feature.HOME.model.ChallengeStatus
import com.relearn.app.feature.HOME.model.DifficultyLevel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChallengeRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val challengesRef = firestore.collection("challenges")

    suspend fun getDailyChallenges(
        habits: List<String>,
        difficulty: DifficultyLevel,
        mood: String,
        energy: Int
    ): List<Challenge> {
        return try {
            val snapshot = challengesRef
                .whereIn("habitTarget", habits)
                .whereEqualTo("nivel", difficulty.name)
                .whereEqualTo("activ", true)
                .whereEqualTo("mood", mood)

                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject<Challenge>() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun markChallengeCompleted(challengeId: String) {
        challengesRef.document(challengeId)
            .update("status", ChallengeStatus.COMPLETED.name)
            .await()
    }

    suspend fun regenerateChallenge(currentChallengeId: String): Challenge? {
        val current = challengesRef.document(currentChallengeId).get().await().toObject<Challenge>()
        return try {
            val altSnapshot = challengesRef
                .whereEqualTo("habitTarget", current?.habitTarget)
                .whereEqualTo("nivel", current?.nivel?.name)
                .whereNotEqualTo("id", current?.id)
                .whereEqualTo("activ", true)
                .get()
                .await()

            altSnapshot.documents.firstOrNull()?.toObject<Challenge>()
        } catch (e: Exception) {
            null
        }
    }
}
