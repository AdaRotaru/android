package com.relearn.app.feature.HOME.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.relearn.app.feature.HOME.model.DailyCheckIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CheckInRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val checkInsRef = firestore.collection("daily_check_ins")

    suspend fun saveCheckIn(checkIn: DailyCheckIn) {
        checkInsRef
            .document("${checkIn.userId}_${checkIn.date}")
            .set(checkIn)
            .await()
    }

    suspend fun getCheckInForToday(userId: String, date: Long): DailyCheckIn? {
        return try {
            val documentId = "${userId}_$date"
            val docSnapshot = checkInsRef.document(documentId).get().await()
            docSnapshot.toObject(DailyCheckIn::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
