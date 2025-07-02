package com.relearn.app.feature.HOME.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.relearn.app.feature.HOME.model.Challenge
import com.relearn.app.feature.HOME.model.ChallengeStatus
import com.relearn.app.feature.HOME.model.DifficultyLevel
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject
import com.google.firebase.firestore.Source

class ChallengeRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val challengesRef = firestore.collection("challenges")
    val today = LocalDate.now().toEpochDay()

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

            snapshot.documents.mapNotNull { doc ->
                doc.toObject<Challenge>()?.copy(date = today).also {
                    Log.d("ChallengeRepo", "Mapped challenge: $it")
                }
            }
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error in getDailyChallenges", e)
            emptyList()
        }
    }

    suspend fun markChallengeCompleted(challengeId: String) {
        try {
            Log.d("ChallengeRepo", "Marking challenge completed: $challengeId")
            challengesRef.document(challengeId)
                .update("status", ChallengeStatus.COMPLETED.name)
                .await()
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error marking challenge completed for $challengeId", e)
        }
    }

    suspend fun regenerateChallenge(currentChallengeId: String): Challenge? {
        Log.d("ChallengeRepo", "regenerateChallenge for $currentChallengeId")
        val current = challengesRef.document(currentChallengeId).get().await().toObject<Challenge>()
        Log.d("ChallengeRepo", "Current challenge: $current")
        if (current?.categorie?.lowercase() == "journal") return null
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
            Log.d("ChallengeRepo", "Fetching journal challenges")
            val snapshot = challengesRef
                .whereEqualTo("categorie", "Journal")
                .get()
                .await()
            Log.d("ChallengeRepo", "Found ${snapshot.documents.size} journal challenges")
            snapshot.documents.mapNotNull { it.toObject<Challenge>() }
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error fetching journal challenges", e)
            emptyList()
        }
    }

    suspend fun deleteUserChallenge(userId: String, date: Long, challengeId: String) {
        try {
            Log.d("ChallengeRepo", "Deleting challenge $challengeId for $userId on $date")
            firestore.collection("userChallenges")
                .document(userId)
                .collection("daily")
                .document(date.toString())
                .collection("challenges")
                .document(challengeId)
                .delete()
                .await()
            Log.d("ChallengeRepo", "Deleted challenge $challengeId for $userId on $date")
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error deleting user challenge $challengeId", e)
        }
    }

    suspend fun getOrGenerateUserChallengesForToday(
        userId: String,
        habits: List<String>,
        difficulty: DifficultyLevel,
        mood: String,
        energy: Int
    ): List<Challenge> {
        val userDailyRef = firestore.collection("userChallenges")
            .document(userId)
            .collection("daily")
            .document(today.toString())
            .collection("challenges")

        val metaDocRef = firestore.collection("userChallenges")
            .document(userId)
            .collection("daily")
            .document(today.toString())

        return try {
            Log.d("ChallengeRepo", "Checking if challenges generated for user $userId today=$today")
            val metaSnapshot = metaDocRef.get().await()
            val challengesGenerated = metaSnapshot.getBoolean("generated") ?: false
            Log.d("ChallengeRepo", "Challenges generated flag = $challengesGenerated")

            if (challengesGenerated) {
                Log.d("ChallengeRepo", "Fetching existing user challenges from Firestore")
                val existing = userDailyRef.get(Source.SERVER).await()
                Log.d("ChallengeRepo", "Found ${existing.documents.size} existing user challenges")
                return existing.documents.mapNotNull { it.toObject(Challenge::class.java) }
            }

            Log.d("ChallengeRepo", "Generating new challenges for user $userId")
            val generated = getDailyChallenges(
                habits = habits,
                difficulty = difficulty,
                mood = mood,
                energy = energy
            ).map { challenge ->
                challenge.copy(
                    status = ChallengeStatus.PENDING,
                    date = today,
                    categorie = "auto"
                )
            }

            generated.forEach { challenge ->
                Log.d("ChallengeRepo", "Saving generated challenge with id=${challenge.id}")
                userDailyRef.document(challenge.id).set(challenge).await()
            }

            metaDocRef.set(mapOf("generated" to true), SetOptions.merge()).await()
            Log.d("ChallengeRepo", "Marked challenges as generated")

            generated
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error in getOrGenerateUserChallengesForToday", e)
            emptyList()
        }
    }

    suspend fun markUserChallengeCompleted(userId: String, date: Long, challengeId: String) {
        try {
            Log.d("ChallengeRepo", "Marking user challenge completed: $challengeId for user $userId on $date")
            firestore.collection("userChallenges")
                .document(userId)
                .collection("daily")
                .document(date.toString())
                .collection("challenges")
                .document(challengeId)
                .update("status", ChallengeStatus.COMPLETED.name)
                .await()
            Log.d("ChallengeRepo", "Marked user challenge $challengeId as completed")
        } catch (e: Exception) {
            Log.e("ChallengeRepo", "Error marking user challenge completed $challengeId", e)
        }
    }
}
