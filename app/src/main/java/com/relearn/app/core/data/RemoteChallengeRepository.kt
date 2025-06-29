package com.relearn.app.core.data

import com.google.firebase.firestore.FirebaseFirestore
import com.relearn.app.feature.HOME.model.Challenge
import com.relearn.app.feature.HOME.model.ChallengeStatus
import kotlinx.coroutines.tasks.await

class RemoteChallengeRepository(
    private val firestore: FirebaseFirestore
)
    //: IChallengeRepository \
{

    private val db = FirebaseFirestore.getInstance()
    private val challengesCollection = db.collection("challenges")

    suspend fun addChallenge(challenge: Challenge) {
        challengesCollection.document(challenge.id).set(challenge).await()
    }

     suspend fun updateChallenge(challenge: Challenge) {
        challengesCollection.document(challenge.id).set(challenge).await()
    }

     suspend fun deleteChallenge(id: String) {
        challengesCollection.document(id).delete().await()
    }

     suspend fun getChallengesByStatus(status: ChallengeStatus): List<Challenge> {
        return challengesCollection
            .whereEqualTo("status", status.name)
            .get()
            .await()
            .toObjects(Challenge::class.java)
    }

     suspend fun getChallenges(): List<Challenge> {
        return challengesCollection
            .get()
            .await()
            .toObjects(Challenge::class.java)
    }

     suspend fun updateChallengeStatus(id: String, status: ChallengeStatus) {
        challengesCollection.document(id)
            .update("status", status.name)
            .await()
    }
}
