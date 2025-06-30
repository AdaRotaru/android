package com.relearn.app.feature.HOME.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.relearn.app.feature.HOME.model.DailyCheckIn
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import javax.inject.Inject

class CheckInRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val checkInsRef = firestore.collection("daily_check_ins")

    suspend fun saveCheckIn(checkIn: DailyCheckIn) {
        checkInsRef //referinta catre colectia din bd
            .document("${checkIn.userId}_${checkIn.date}")
            .set(checkIn)
            .await()
    }

    suspend fun getCheckInForToday(userId: String, date: Long): DailyCheckIn? { //params pt userid si data
        return try {
            val documentId = "${userId}_${LocalDate.now().toEpochDay()}"
            val docSnapshot = checkInsRef.document(documentId).get().await()
            docSnapshot.toObject(DailyCheckIn::class.java) //face maparea din json in obiect
        } catch (e: Exception) {
            null //retun null in caz de esec
        }
    }
}
