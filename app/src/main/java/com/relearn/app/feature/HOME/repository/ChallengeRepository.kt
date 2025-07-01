package com.relearn.app.feature.HOME.repository

import android.util.Log
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
            Log.d("ChallengeRepo", "Fetching with habits=$habits, difficulty=${difficulty.name}, mood=$mood")

            val snapshot = challengesRef
                .whereIn("habitTarget", habits)
                .whereEqualTo("nivel", difficulty.name)
                .whereEqualTo("activ", true)
                .whereEqualTo("mood", mood.lowercase().replaceFirstChar { it.uppercase() })
                .get()
                .await()

            Log.d("ChallengeRepo", "Found ${snapshot.documents.size} documents")
            snapshot.documents.forEach { doc ->
                Log.d("ChallengeRepo", "Doc data: ${doc.data}")
            }

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
        Log.d("ChallengeRepo", "regenerateChallenge for $currentChallengeId")
        val current = challengesRef.document(currentChallengeId).get().await().toObject<Challenge>()
        Log.d("ChallengeRepo", "Current challenge: $current")
        return try {
            val altSnapshot = challengesRef
                .whereEqualTo("habitTarget", current?.habitTarget)
                .whereEqualTo("nivel", current?.nivel?.name)
                .whereNotEqualTo("id", current?.id)
                .whereEqualTo("activ", true)
                .get()
                .await()
            Log.d("ChallengeRepo", "Found ${altSnapshot.documents.size} alternative challenges")
            altSnapshot.documents.firstOrNull()?.toObject<Challenge>()
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error regenerating challenge", e)
            null
        }
    }
    suspend fun getJournalChallenges(): List<Challenge> {
        return try {
            challengesRef
                .whereEqualTo("categorie", "Journal")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject<Challenge>() }
        } catch (e: Exception) {
            emptyList()
        }
    }
    suspend fun deleteChallenge(challengeId: String) {
        challengesRef.document(challengeId).delete().await()
    }

}
